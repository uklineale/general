package com.seniordesigndbgt.dashboardtest.modeltests;

import com.seniordesigndbgt.dashboard.model.Module;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by kamehardy on 3/27/16.
 */
public class ModuleTest {

    @Test
    public void testGetId() throws Exception {
        Module module = new Module();
        Module preset = new Module("title", "end", "frag");
        assertEquals(0, module.getId());
        module.setId(12);
        assertEquals(12, module.getId());
        assertEquals(0, preset.getId());
    }


    @Test
    public void testGetTitle() throws Exception {
        Module module = new Module();
        Module preset = new Module("title", "end", "frag");
        preset = new Module("title", "end");
        assertNull( module.getTitle());
        module.setTitle("POTATO");
        assertEquals("POTATO", module.getTitle());
        assertEquals("title", preset.getTitle());
    }

    @Test
    public void testGetApiEndpoint() throws Exception {
        Module module = new Module();
        Module preset = new Module("title", "end", "frag");
        assertNull( module.getApiEndpoint());
        module.setApiEndpoint("potato.com/potato");
        assertEquals("potato.com/potato", module.getApiEndpoint());
        assertEquals("end", preset.getApiEndpoint());
    }

    @Test
    public void testGetFragment() throws Exception {
        Module module = new Module();
        Module preset = new Module("title", "end", "frag");
        assertNull(module.getFragment());
        module.setFragment("potato");
        assertEquals("potato", module.getFragment());
        assertEquals("frag", preset.getFragment());
    }
}