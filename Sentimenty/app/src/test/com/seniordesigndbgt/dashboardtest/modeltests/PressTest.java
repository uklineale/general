package com.seniordesigndbgt.dashboardtest.modeltests;

import org.junit.Test;
import com.seniordesigndbgt.dashboard.model.Press;
import static org.junit.Assert.*;

import java.time.Instant;
import java.util.*;

/**
 * Created by kamehardy on 3/27/16.
 */
public class PressTest {

    Date date = new Date();

    @Test
    public void testGetId() throws Exception {
        Press p = new Press();
        Press preset = new Press("source", "url", "title", date, "thumbnail");
        assertEquals(0, p.getId());
        p.setId(100);
        assertEquals(100, p.getId());
        assertEquals(0, preset.getId());
        p.setId(101);
        assertEquals(101, p.getId());
    }

    @Test
    public void testGetSource() throws Exception {
        Press p = new Press();
        Press preset = new Press("source", "url", "title", date, "thumbnail");
        p.setSource("str");
        assertEquals("str", p.getSource());
        assertEquals("source", preset.getSource());
        preset.setSource("sources");
        assertEquals("sources", preset.getSource());
    }

    @Test
    public void testGetUrl() throws Exception {
        Press p = new Press();
        Press preset = new Press("source", "url", "title", date, "thumbnail");
        assertNull( p.getUrl());
        p.setUrl("www.bloomberg.com");
        assertEquals("www.bloomberg.com", p.getUrl());
        assertEquals("url", preset.getUrl());
        preset.setUrl("uurl");
        assertEquals("uurl", preset.getUrl());
    }

    @Test
    public void testGetTitle() throws Exception {
        Press p = new Press();
        Press preset = new Press("source", "url", "title", date, "thumbnail");
        assertNull(p.getTitle());
        p.setTitle("Article");
        assertEquals("Article", p.getTitle());
        assertEquals("title", preset.getTitle());
        preset.setTitle("fiRe");
        assertEquals("fiRe", preset.getTitle());
    }


    @Test
    public void testGetSentiment() throws Exception {
        Press p = new Press();
        Press preset = new Press("source", "url", "title", date, "thumbnail");
        assertNull(p.getSentiment());
        p.setSentiment("Positive");
        assertEquals("Positive", p.getSentiment());
        assertNull(preset.getSentiment());
    }

    @Test
    public void testGetKeywords() throws Exception {
        Press p = new Press();
        Press preset = new Press("source", "url", "title", date, "thumbnail");
        assertNull(p.getKeywords());
        p.setKeywords("art");
        assertEquals("art", p.getKeywords());
        assertNull(preset.getKeywords());
        p.setKeywords("papers");
        assertEquals("papers", p.getKeywords());
    }


    @Test
    public void testGetTime() throws Exception {
        Press p = new Press();
        Press preset = new Press("source", "url", "title", date, "thumbnail");
        assertNull(p.getTime());
        Date today = new Date();
        p.setTime(today);
        assertEquals(today, p.getTime());
        assertEquals(date, preset.getTime());
        p.setTime(date);
        assertEquals(date, p.getTime());
    }

    @Test
    public void testGetThumbnail() throws Exception {
        Press p = new Press();
        Press preset = new Press("source", "url", "title", date, "thumbnail");
        assertNull(p.getThumbnail());
        p.setThumbnail("thumb");
        assertEquals("thumb", p.getThumbnail());
        assertEquals("thumbnail", preset.getThumbnail());
        p.setThumbnail("thumbs");
        assertEquals("thumbs", p.getThumbnail());
    }

    @Test
    public void testGetBody() throws Exception {
        Press p = new Press();
        Press preset = new Press("source", "url", "title", date, "thumbnail");
        assertNull(p.getBody());
        assertNull(preset.getBody());
        p.setBody("thumb");
        preset.setBody("thumbnail");
        assertEquals("thumb", p.getBody());
        assertEquals("thumbnail", preset.getBody());
        p.setBody("thumbs");
        assertEquals("thumbs", p.getBody());
    }
}