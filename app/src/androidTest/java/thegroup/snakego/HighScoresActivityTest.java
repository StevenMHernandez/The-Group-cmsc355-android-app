package thegroup.snakego;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;

import thegroup.snakego.database.HighScores;
import thegroup.snakego.interfaces.HttpResultsInterface;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


@RunWith(AndroidJUnit4.class)
public class HighScoresActivityTest {

    @Rule
    public ActivityTestRule<HighScoresActivity> mHighScoresActivity = new ActivityTestRule<>(HighScoresActivity.class);

    @Test
    public void onOptionsPageClickHighScoresBackToOptions() {
        // given user is on High Scores Activity page
        onView(withId(R.layout.activity_high_scores));

        // when user clicks "Return to Options Page"
        onView(withId(R.id.return_to_options_page)).perform(click());

        // then user is taken to Options page
        onView(withId(R.id.options_page_text)).check(matches(notNullValue()));
    }

    @Test
    public void requestsHighscoreFromServer() {
        // create mock objects
        HttpResultsInterface mockedCallback = mock(HttpResultsInterface.class);
        RequestQueue mockedRequestQueue = mock(RequestQueue.class);

        // create new highscores api requester
        HighScores highScores = new HighScores(null, mockedCallback);

        // set our mock request queue
        highScores.setRequestQueue(mockedRequestQueue);

        // attempt to load highscores
        highScores.load();

        // check that a request was added to the request queue
        verify(mockedRequestQueue, atLeastOnce()).add(any(Request.class));
    }

    @Test
    public void postsHighscoreToServer() {
        // create mock objects
        HttpResultsInterface mockedCallback = mock(HttpResultsInterface.class);
        RequestQueue mockedRequestQueue = mock(RequestQueue.class);

        // create new highscores api requester
        HighScores highScores = new HighScores(null, mockedCallback);

        // set our mock request queue
        highScores.setRequestQueue(mockedRequestQueue);

        // attempt to load highscores
        highScores.store("Example User Name", 999);

        // check that a request was added to the request queue
        verify(mockedRequestQueue, atLeastOnce()).add(any(Request.class));
    }

    @Test
    public void highscoreRequestsCorrectAPIEndpoint() {
        // create mock objects
        HttpResultsInterface mockedCallback = mock(HttpResultsInterface.class);
        RequestQueue mockedRequestQueue = mock(RequestQueue.class);

        // create new highscores api requester
        HighScores highScores = new HighScores(null, mockedCallback);

        // set our mock request queue
        highScores.setRequestQueue(mockedRequestQueue);

        // attempt to load highscores
        highScores.load();

        // capture the argument that was passed to .add()
        ArgumentCaptor<Request> argument = ArgumentCaptor.forClass(Request.class);
        verify(mockedRequestQueue).add(argument.capture());

        // make sure that the url endpoint is correct
        assertEquals("http://snake-go.shmah.com/highscores", argument.getValue().getUrl());

        // make sure that the request is a GET request
        assertEquals(Request.Method.GET, argument.getValue().getMethod());
    }

    @Test
    public void highscorePostRequestsCorrectAPIEndpoint() {
        // create mock objects
        HttpResultsInterface mockedCallback = mock(HttpResultsInterface.class);
        RequestQueue mockedRequestQueue = mock(RequestQueue.class);

        // create new highscores api requester
        HighScores highScores = new HighScores(null, mockedCallback);

        // set our mock request queue
        highScores.setRequestQueue(mockedRequestQueue);

        // attempt to store a new highscore
        highScores.store("Example User Name", 999);

        // capture the argument that was passed to .add()
        ArgumentCaptor<JsonObjectRequest> argument = ArgumentCaptor.forClass(JsonObjectRequest.class);
        verify(mockedRequestQueue).add(argument.capture());

        // make sure that the url endpoint is correct
        assertEquals("http://snake-go.shmah.com/highscores", argument.getValue().getUrl());

        // make sure that the request is a POST request
        assertEquals(Request.Method.POST, argument.getValue().getMethod());
    }

}