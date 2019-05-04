package data.Subscriprions;
import static org.mockito.Mockito.*;

import data.Port.Vessel;
import managers.OpcBot;
import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;

class UpdateBerthsTest extends UpdateBerths {
    private static TreeMap<String, User> users = new TreeMap<>();
    private static TreeMap<String, List<Vessel>> map;

    String solomon = "2 King Solomon true";
    String arthur = "10 King Arthur false";
    String ludvig = "22 King Ludvig true";


    @BeforeAll
    static void init() {
        List<Vessel> oldVessel = new ArrayList<>();
        oldVessel.add(new Vessel("2", "Sunshine King", "02/02/2012"));
        oldVessel.add(new Vessel("10", "King Arthur", "10/02/2012"));
        List<Vessel> newVessel = new ArrayList<>();
        newVessel.add(new Vessel("2", "King Solomon", "02/02/2012"));
        newVessel.add(new Vessel("2", "Sunshine King", "02/02/2012"));
        newVessel.add(new Vessel("22", "King Ludvig", "22/02/2016"));

        users.put("id1", new User().setUserChatId("id1").setBerthUpdateSubscription("true")
                .setBerthsSelected("2 22 10"));
        users.put("id2", new User().setUserChatId("id2").setBerthUpdateSubscription("true")
                .setBerthsSelected("40"));
        users.put("id3", new User().setUserChatId("id3").setBerthUpdateSubscription("true")
                .setBerthsSelected(""));
        users.put("id4", new User().setUserChatId("id4").setBerthUpdateSubscription("true")
                .setBerthsSelected("22"));
        users.put("id5", new User().setUserChatId("id5").setBerthUpdateSubscription("false")
                .setBerthsSelected("22"));
        map = getChangesOnPortUpdate(oldVessel, newVessel);
    }

    @Test
    void testMap(){
        Assert.assertEquals(3, map.size());
        List<Vessel> list = map.get("10");
        Assert.assertEquals(arthur, "10 ".concat(list.get(0).getVesselName())
                .concat(" ").concat(String.valueOf(list.get(0).isMoored())));

        list = map.get("2");
        Assert.assertEquals(solomon, "2 ".concat(list.get(0).getVesselName())
                .concat(" ").concat(String.valueOf(list.get(0).isMoored())));

        list = map.get("22");
        Assert.assertEquals(ludvig , "22 ".concat(list.get(0).getVesselName())
                .concat(" ").concat(String.valueOf(list.get(0).isMoored())));
    }

    @Test
    void testSender(){
        OpcBot bot = mock(OpcBot.class);
        Set<String> set = new HashSet<>();
        set.add("id1 2 King Solomon true");
        set.add("id1 10 King Arthur false");
        set.add("id1 22 King Ludvig true");
        set.add("id4 22 King Ludvig true");

        Assert.assertEquals(set , sendInfoAboutBerths(users, map, bot));
    }

    class StringAnswer implements Answer {
        @Override
        public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
            Object[] arguments = invocationOnMock.getArguments();
            String chatId = (String)arguments[0];
            Vessel vessel = (Vessel)arguments[1];
            return chatId.concat(" ").concat(vessel.getVesselName()).concat(" ")
                    .concat(String.valueOf(vessel.isMoored()));
        }
    }
}