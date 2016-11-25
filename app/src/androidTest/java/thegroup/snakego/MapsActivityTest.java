package thegroup.snakego;

import android.graphics.Color;
import android.os.Looper;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import thegroup.snakego.models.User;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;

@RunWith(AndroidJUnit4.class)
public class MapsActivityTest {

    @Rule
    public ActivityTestRule<MapsActivity> mMapsActivity = new ActivityTestRule<>(MapsActivity.class);

    @Before
    public void setup() {
        // required for blinkScoreText()
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
    }

    @Test
    public void onClickSnakeGoIconToOptionsPage() {
        // Display Menu user issue: Scenario 1: given user is playing game
        String optionsActivityText = "Resume Game";

        // when user clicks on the snakeGo icon button
        onView(withId(R.id.icon_button)).perform(click());

        // then the user lands on the options page and Sees "Resume Game" text
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

    @Test
    public void onClickSnakeGoIconToOptionsPageHighScore() {
        // Display Menu user issue: Scenario 3: given user is playing game
        String optionsActivityText = "High Scores";

        // when user clicks on the snakeGo icon button
        onView(withId(R.id.icon_button)).perform(click());

        // then the user lands on the options page and sees "High Scores" text
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

    @Test
    public void onClickSnakeGoIconToOptionsQuitGame() {
        // given user is playing game
        String optionsActivityText = "Quit Game";

        // when user clicks on the snakeGo icon button
        onView(withId(R.id.icon_button)).perform(click());

        // then the user lands on the options page and sees "Quit Game" text
        onView(withText(optionsActivityText)).check(matches(notNullValue()));
    }

    @Test
    public void onStartUpMapAppears() {
        // Map Display user issue: Scenario 1: given user clicks on SnageGo app,
        // the MapsActivity begins

        onView(withId(R.id.map)).check(matches(notNullValue()));
    }


    @Test
    public void returnFromOptionsPageafterPause() {
        //  Given user is on options page after clicking icon button, if user
        // clicks on Resume Game, he will return to the game
        onView(withId(R.id.icon_button)).perform(click());
        onView(withId(R.id.resume_game_text)).perform(click());
        onView(withId(R.id.map)).check(matches(notNullValue()));
    }

    @Test
    public void jsonMapStyleLoads() {
        //  Given that the user starts the app, when the game starts automatically, the user
        //  sees that the landscape is green, the roads are yellow, there are no labels, and
        //  the water is purple.  This tests that the json object is passed to Google Services
        Assert.assertTrue(MapsActivity.jsonFlag);
    }

    @Test
    public void milestonePropertyChangeToastTest() {


        User.get().addPoints(350);
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

    @Test
    public void showMyScoreOnTheMapsPage() {
        int score = User.get().getScore();

        onView(withId(R.id.score))
                .check(matches(withText(Integer.toString(score))));
    }

    @Test
    public void scoreUpdatesWhenIGainPoints() throws Throwable {
        final User user = User.get();

        mMapsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user.addPoints(100);
            }
        });

        int score = user.getScore();

        onView(withId(R.id.score))
                .check(matches(withText(Integer.toString(score))));
    }

    @Test
    public void scoreUpdatesWhenILosePoints() throws Throwable {
        final User user = User.get();

        mMapsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                user.addPoints(1000);

                user.removePoints(100);
            }
        });

        int score = user.getScore();

        onView(withId(R.id.score))
                .check(matches(withText(Integer.toString(score))));
    }

    @Test
    public void scoreTextBlinksBlueWhenIGainPoints() throws Throwable {
        final MapsActivity spyActivity = Mockito.spy(this.mMapsActivity.getActivity());

        mMapsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // score goes up from 0 to 100
                spyActivity.updateScore(0, 100);
            }
        });

        Mockito.verify(spyActivity).blinkScoreText(Color.BLUE);
    }

    @Test
    public void scoreTextBlinksRedWhenILosePoints() throws Throwable {
        final MapsActivity spyActivity = Mockito.spy(this.mMapsActivity.getActivity());

        mMapsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // score goes down from 100 to 0
                spyActivity.updateScore(100, 0);
            }
        });

        Mockito.verify(spyActivity).blinkScoreText(Color.RED);
    }

    @Test
    public void scoreTextDoesNotBlinkIfMyScoreStaysTheSame() throws Throwable {
        final MapsActivity spyActivity = Mockito.spy(this.mMapsActivity.getActivity());

        mMapsActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                spyActivity.updateScore(100, 100);
            }
        });

        Mockito.verify(spyActivity, never()).blinkScoreText(anyInt());
    }
}