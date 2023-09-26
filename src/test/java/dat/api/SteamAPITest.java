package dat.api;

import dat.util.api.SteamAPI;
import dat.util.api.SteamCall;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SteamAPITest {

    @Test
    void getSteamNewsPositive() {
        SteamCall steamCall = SteamAPI.call(730);
        assertNotNull(steamCall);
        assertEquals(730, steamCall.getApp_id());
    }

    @Test
    void getSteamNewsNegative() {
        SteamCall steamCall = SteamAPI.call(-730);
        assertNull(steamCall);
    }
}