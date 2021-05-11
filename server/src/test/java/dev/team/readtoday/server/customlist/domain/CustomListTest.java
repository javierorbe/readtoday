package dev.team.readtoday.server.customlist.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class CustomListTest {

  @Test
  void shouldEqualIfSameInstance(){
    CustomList list = CustomListMother.random();
    assertEquals(list, list);
  }

  @Test
  void shoulNotBeEqualToNull(){
    CustomList list = CustomListMother.random();
    assertFalse(list.equals(null));
  }

 /* @Test
  void shouldBeEqualsIfSameId(){
    CustomList espectedList = CustomListMother.random();
    CustomList list = CustomListMother.randomWithUser(espectedList.getUserId());
    assertEquals(espectedList, list);

  }*/
}
