package me.geekymind.bakingapp.ui.recipes;

import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import me.geekymind.bakingapp.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RecipesActivityTest {

  @Rule
  public ActivityTestRule<RecipesActivity> mActivityTestRule =
      new ActivityTestRule<>(RecipesActivity.class);

  @Test
  public void recipesActivityTest() {
    ViewInteraction textView = onView(allOf(withId(R.id.recipe_title), withText("Nutella Pie"),
        childAtPosition(childAtPosition(withId(R.id.item_recipe_container), 0), 1), isDisplayed()));
    textView.check(matches(withText(endsWith("Nutella Pie"))));

    ViewInteraction textView2 = onView(allOf(withId(R.id.recipe_title), withText("Yellow Cake"),
        childAtPosition(childAtPosition(withId(R.id.item_recipe_container), 0), 1), isDisplayed()));
    textView2.check(matches(withText(endsWith("Yellow Cake"))));
  }

  private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher,
      final int position) {

    return new TypeSafeMatcher<View>() {
      @Override
      public void describeTo(Description description) {
        description.appendText("Child at position " + position + " in parent ");
        parentMatcher.describeTo(description);
      }

      @Override
      public boolean matchesSafely(View view) {
        ViewParent parent = view.getParent();
        return parent instanceof ViewGroup && parentMatcher.matches(parent) && view.equals(
            ((ViewGroup) parent).getChildAt(position));
      }
    };
  }
}
