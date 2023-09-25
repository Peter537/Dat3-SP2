package dat.peter.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SteamAPITest {

    @Test
    void getSteamNewsPositive() {
        SteamAPI steamAPI = new SteamAPI();
        SteamCall steamCall = steamAPI.call(730);
        assertEquals(730, steamCall.getApp_id());
    }

    @Test
    void getSteamNewsNegative() {
        SteamAPI steamAPI = new SteamAPI();
        SteamCall steamCall = steamAPI.call(-730);
        assertNull(steamCall);
    }

}