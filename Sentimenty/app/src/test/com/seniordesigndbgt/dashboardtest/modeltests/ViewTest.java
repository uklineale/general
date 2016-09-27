package com.seniordesigndbgt.dashboardtest.modeltests;

import com.seniordesigndbgt.dashboard.model.User;
import org.junit.Test;
import com.seniordesigndbgt.dashboard.model.View;
import com.seniordesigndbgt.dashboard.model.Module;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by kamehardy on 3/27/16.
 */
public class ViewTest {

    @Test
    public void testGetUsername() throws Exception {
        View view = new View(new User("potato"), "potato", new ArrayList<Module>(), new ArrayList<Module>(), new ArrayList<Module>());
        assertEquals("potato", view.getUsername().getUsername());
        view.setUsername(new User());
        assertNull(view.getUsername().getUsername());
    }

    @Test
    public void testGetName() throws Exception {
        View view = new View(new User("potato"), "potato", new ArrayList<Module>(), new ArrayList<Module>(), new ArrayList<Module>());
        assertEquals("potato", view.getName());
        view.setName("tot");
        assertEquals("tot", view.getName());
    }


    @Test
    public void testGetColumnOne() throws Exception {
        View view = new View(new User("potato"), "potato", null, new ArrayList<Module>(), new ArrayList<Module>());
        assertNull(view.getColumnOne());
        view.setColumnOne(new ArrayList<Module>());
        assertEquals(new ArrayList<Module>(), view.getColumnOne());
    }


    @Test
    public void testGetColumnTwo() throws Exception {
        View view = new View(new User("potato"), "potato", new ArrayList<Module>(), null, new ArrayList<Module>());
        assertNull(view.getColumnTwo());
        view.setColumnTwo(new ArrayList<Module>());
        assertEquals(new ArrayList<Module>(), view.getColumnTwo());
    }


    @Test
    public void testGetColumnThree() throws Exception {
        View view = new View(new User("potato"), "potato", new ArrayList<Module>(), new ArrayList<Module>(), null);
        assertNull(view.getColumnThree());
        view.setColumnThree(new ArrayList<Module>());
        assertEquals(new ArrayList<Module>(), view.getColumnThree());
    }

}