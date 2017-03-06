package com.cmpt276.kenneyw.carbonfootprinttracker;

import org.junit.Test;

import static org.junit.Assert.*;

public class RouteTest {
    @Test
    public void constructorTest() throws Exception {
        Route route=new Route("Test",100,100);
        assertEquals(route.getRouteName(),"Test");
        assertEquals(route.getCityDistance(),100);
        assertEquals(route.getHighwayDistance(),100);
        assertEquals(route.getTotalDistance(),200);

    }

    @Test
    public void setAndGetTest() throws Exception {
        Route route=new Route("Test",100,100);
        route.setRouteName("Test 2");
        route.setCityDistance(200);
        route.setHighwayDistance(200);

        assertEquals(route.getRouteName(),"Test 2");
        assertEquals(route.getCityDistance(),200);
        assertEquals(route.getHighwayDistance(),200);
        assertEquals(route.getTotalDistance(),400);
    }

    @Test
    public void toStringTest() throws Exception {

    }

}