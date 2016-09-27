package com.seniordesigndbgt.dashboardtest.modeltests;

import org.junit.Test;
import com.seniordesigndbgt.dashboard.model.Twitter;

import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Created by kamehardy on 3/27/16.
 */
public class TwitterTest {

    @Test
    public void testGetId() throws Exception {
        long val = 100000;
        Twitter tweet = new Twitter();
        assertEquals(0, tweet.getId());
        tweet.setId(val);
        assertEquals(val, tweet.getId());
        tweet.setId(val+1);
        assertNotEquals(val, tweet.getId());
        assertEquals(val+1, tweet.getId());
    }


    @Test
    public void testGetText() throws Exception {
        String str = "Hello world";
        Twitter tweet = new Twitter();
        assertNull(tweet.getText());
        tweet.setText(str);
        assertEquals(str, tweet.getText());
        tweet.setText(str + "potato");
        assertNotEquals(str, tweet.getText());
        assertEquals(str + "potato", tweet.getText());
    }

    @Test
    public void testToString() throws Exception {
        String handle = "dbcares";
        String text = "hello world";
        String empty = " ";
        Twitter tweet = new Twitter();
        assertNull(tweet.toString());
        tweet = new Twitter(handle, text, empty,empty, Timestamp.valueOf("1111-11-11 11:12:12"));
        assertEquals("@"+ handle + " - " + text, tweet.toString());
    }

    @Test
    public void testImage() throws Exception {
        Twitter tweet = new Twitter("handle", "text", "empty","image", Timestamp.valueOf("1111-11-11 11:12:12"));
        assertEquals("image", tweet.getImage());
        tweet.setImage("jpg");
        assertEquals("jpg", tweet.getImage());
    }

    @Test
    public void testCreated() throws Exception {
        Twitter tweet = new Twitter("handle", "text", "empty","image", Timestamp.valueOf("1111-11-11 11:12:12"));
        assertEquals(Timestamp.valueOf("1111-11-11 11:12:12"), tweet.getCreated());
        tweet.setCreated(Timestamp.valueOf("1111-11-11 11:12:13"));
        assertEquals(Timestamp.valueOf("1111-11-11 11:12:13"), tweet.getCreated());
    }

    @Test
    public void testURL() throws Exception {
        Twitter tweet = new Twitter("handle", "text", "empty","image", Timestamp.valueOf("1111-11-11 11:12:12"));
        assertEquals("empty", tweet.getUrl());
    }
}