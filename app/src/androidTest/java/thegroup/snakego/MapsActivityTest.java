package thegroup.snakego;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(AndroidJUnit4.class)
public class MapsActivityTest {

    @Rule public ActivityTestRule<MapsActivity> mMapsActivity = new ActivityTestRule<>(MapsActivity.class);

    @Test public void onClickSnakeGoIconToOptionsPage() {
        // Display Menu user issue: Scenario 1: given user is playing game
        String optionsActivityText = "Resume Game";

        // when user clicks on the snakeGo icon button
        onView(withId(R.id.icon_button)).perform(click());

        // then the user lands on the options page and Sees "Resume Game" text
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

    @Test public void onClickSnakeGoIconToOptionsPageHighScore() {
        // Display Menu user issue: Scenario 3: given user is playing game
        String optionsActivityText = "High Scores";

        // when user clicks on the snakeGo icon button
        onView(withId(R.id.icon_button)).perform(click());

        // then the user lands on the options page and sees "High Scores" text
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

    @Test public void onClickSnakeGoIconToOptionsQuitGame() {
        // given user is playing game
        String optionsActivityText = "Quit Game";

        // when user clicks on the snakeGo icon button
        onView(withId(R.id.icon_button)).perform(click());

        // then the user lands on the options page and sees "Quit Game" text
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

    @Test public void onStartUpMapAppears() {
        // Map Display user issue: Scenario 1: given user clicks on SnageGo app,
        // the MapsActivity begins

        onView(withId(R.id.map)).check(matches(notNullValue()));
    }


    @Test public void returnFromOptionsPageafterPause() {
        //  Given user is on options page after clicking icon button, if user
        // clicks on Resume Game, he will return to the game
        onView(withId(R.id.icon_button)).perform(click());
        onView(withId(R.id.resume_game_text)).perform(click());
        onView(withId(R.id.map)).check(matches(notNullValue()));
    }

    @Test public void jsonMapStyleLoads() {
        //  Given that the user starts the app, when the game starts automatically, the user
        //  sees that the landscape is green, the roads are yellow, there are no labels, and
        //  the water is purple.  This tests that the json object is passed to Google Services
        Assert.assertTrue(MapsActivity.jsonFlag);
    }

    @Test
    public void milestonePropertyChangeToastTest(){
        //  Given that the user starts the app, when the game starts automatically, the user
        //  sees his first milestone, Just a Walk in the Park, which trips the flag to true
        //  in the MapsActivity.class, which is the main activity
        Assert.assertTrue(MapsActivity.PropertyChangeFlag);
    }

    @Test
    public void onOptionsPageClickHighScoresBackToOptions() {
        // given user is on High Scores Activity page
        onView(withId(R.id.icon_button)).perform(click());
        onView(withId(R.id.high_scores_text)).perform(click());

        // when user clicks "Return to Options Page"
        onView(withId(R.id.return_to_options_page)).perform(click());


        // then user is taken to Options page
        onView(withId(R.id.options_page_text)).check(matches(notNullValue()));
    }



}