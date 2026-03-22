package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rebeyka.acapi.actionables.Actionable;
import com.rebeyka.acapi.builders.PlayFactory;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.entities.gameflow.Ranking;
import com.rebeyka.acapi.entities.gameflow.Timeline;
import com.rebeyka.acapi.entities.gameflow.Trigger;
import com.rebeyka.acapi.exceptions.GameElementNotFoundException;

public class GameTest {

	@Mock
	private Player mockPlayer;

	@Mock
	private Player mockOpponent;

	@Mock
	private Timeline mockTimeline;

	@Mock
	private PlayFactory mockPlayFactory;

	private Game game;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		game = new Game("TEST", List.of(mockPlayer, mockOpponent));
		game.setTimeline(mockTimeline);
		game.setPlayFactory(mockPlayFactory);

		when(mockPlayer.getGame()).thenReturn(game);
		when(mockOpponent.getGame()).thenReturn(game);

	}

	@Test
	public void testDeclarePlay() {
		Play play = mock(Play.class);
		when(play.isPossible()).thenReturn(true);
		when(mockPlayFactory.copyOf(play, List.of(mockPlayer))).thenReturn(play);
		when(mockTimeline.hasNext()).thenReturn(false);

		assertThat(game.declarePlay(play, mockPlayer)).isTrue();
		verify(mockTimeline).hasNext();
		verify(mockTimeline).queue(play, false);
		verify(play).isPossible();
		verify(play).getOrigin();
		verify(play).getName();
		verifyNoMoreInteractions(mockTimeline);
		verifyNoMoreInteractions(play);
		assertThat(game.getQueuedPlays()).isEmpty();
	}

	@Test
	public void testDeclarePlayQueued() {
		Play play = mock(Play.class);
		when(mockPlayFactory.copyOf(play, List.of(mockPlayer))).thenReturn(play);
		when(mockTimeline.hasNext()).thenReturn(true);

		assertThat(game.declarePlay(play, mockPlayer)).isTrue();
		verify(mockTimeline).hasNext();
		verify(play).getOrigin();
		verify(play).getName();
		verifyNoMoreInteractions(mockTimeline);
		verifyNoMoreInteractions(play);
		assertThat(game.getQueuedPlays()).containsExactly(play);
	}

	@Test
	public void testPlayNotPossible() {
		Play play = mock(Play.class);
		when(play.isPossible()).thenReturn(false);
		when(mockPlayFactory.copyOf(play, List.of(mockPlayer))).thenReturn(play);
		when(mockTimeline.hasNext()).thenReturn(false);

		assertThat(game.declarePlay(play, mockPlayer)).isFalse();
		verify(mockTimeline).hasNext();
		verify(play).getOrigin();
		verify(play).getName();
		verify(play).isPossible();
		verifyNoMoreInteractions(mockTimeline);
		verifyNoMoreInteractions(play);
		assertThat(game.getQueuedPlays()).isEmpty();
	}

	@Test
	public void testPlaySkipQueue() {
		Play play = mock(Play.class);
		when(play.isPossible()).thenReturn(true);
		when(mockPlayFactory.copyOf(play, List.of(mockPlayer))).thenReturn(play);
		when(mockTimeline.hasNext()).thenReturn(true);

		assertThat(game.declarePlay(play, List.of(mockPlayer), true)).isTrue();
		verify(mockTimeline).hasNext();
		verify(mockTimeline).queue(play, true);
		verify(play).isPossible();
		verify(play).getOrigin();
		verify(play).getName();
		verifyNoMoreInteractions(mockTimeline);
		verifyNoMoreInteractions(play);
		assertThat(game.getQueuedPlays()).isEmpty();
	}

	@Test
	public void testExecuteNextQueuedPlays() {
		Play queuedPlay1 = mock(Play.class);
		Play queuedPlay2 = mock(Play.class);
		Ranking ranking = mock(Ranking.class);
		game.setRanking(ranking);
		game.getQueuedPlays().addAll(List.of(queuedPlay1, queuedPlay2));

		when(mockTimeline.hasNext()).thenReturn(false);
		when(mockTimeline.executeNext()).thenReturn(true);
		when(queuedPlay1.isPossible()).thenReturn(true);
		when(queuedPlay2.isPossible()).thenReturn(false);

		assertThat(game.executeNext()).isTrue();
		verify(mockTimeline, times(3)).hasNext();
		verify(mockTimeline).executeNext();
		verify(mockTimeline).queue(queuedPlay1);
		verify(queuedPlay1).isPossible();
		verify(queuedPlay2).isPossible();
		verify(ranking, times(2)).updateRanking(List.of(mockPlayer, mockOpponent));
		verifyNoMoreInteractions(mockTimeline, queuedPlay1, queuedPlay2, ranking);

	}

	@Test
	public void testExecuteNextHasNext() {
		when(mockTimeline.executeNext()).thenReturn(true);
		when(mockTimeline.hasNext()).thenReturn(true);

		assertThat(game.executeNext()).isTrue();
		verify(mockTimeline).executeNext();
		verify(mockTimeline).hasNext();
		verifyNoMoreInteractions(mockTimeline);
	}

	@Test
	public void testExecuteNextTimelineFail() {
		when(mockTimeline.executeNext()).thenReturn(false);

		assertThat(game.executeNext()).isFalse();
		verify(mockTimeline).executeNext();
		verifyNoMoreInteractions(mockTimeline);
	}

	@Test
	public void testExecuteAll() {
		when(mockTimeline.executeNext()).thenReturn(true, false);
		assertThat(game.executeAll()).isTrue();
		verify(mockTimeline, times(2)).executeNext();
		verify(mockTimeline).hasNext();
		verifyNoMoreInteractions(mockTimeline);
	}

	@Test
	public void testExecuteAllNoExecutions() {
		when(mockTimeline.executeNext()).thenReturn(false);
		assertThat(game.executeAll()).isFalse();
		verify(mockTimeline).executeNext();
		verifyNoMoreInteractions(mockTimeline);
	}

	@Test
	public void testBeforeTriggersFollowPriority() {
		Actionable actionable = mock(Actionable.class);
		Trigger lateTrigger = mock(Trigger.class);
		when(lateTrigger.test(actionable)).thenReturn(true);
		Trigger earlyTrigger = mock(Trigger.class);
		when(earlyTrigger.test(actionable)).thenReturn(true);
		Trigger anotherEarlyTrigger = mock(Trigger.class);

		Play earlyPlay = mock(Play.class);
		Play latePlay = mock(Play.class);
		when(mockPlayFactory.fromTrigger(null, earlyTrigger)).thenReturn(earlyPlay);
		when(mockPlayFactory.fromTrigger(null, lateTrigger)).thenReturn(latePlay);

		game.registerBeforeTrigger(10, lateTrigger);
		game.registerBeforeTrigger(earlyTrigger);
		game.registerBeforeTrigger(anotherEarlyTrigger);
		assertThat(game.getBeforeTriggerActionables(actionable)).hasSize(2).containsExactly(earlyPlay, latePlay);
		verify(earlyTrigger).test(actionable);
		verify(anotherEarlyTrigger).test(actionable);
		verify(lateTrigger).test(actionable);
		verifyNoMoreInteractions(earlyTrigger, anotherEarlyTrigger, lateTrigger);
	}

	@Test
	public void testBeforeTriggerUniquelyAdded() {
		Trigger trigger = mock(Trigger.class);
		game.registerBeforeTrigger(trigger);
		game.registerBeforeTrigger(trigger);
		game.unregisterBeforeTrigger(trigger);
		assertThat(game.getBeforeTriggerActionables(null)).isEmpty();
		verifyNoInteractions(trigger);
	}

	@Test
	public void testBeforeTriggerRemoveInvalid() {
		assertThatThrownBy(() -> game.unregisterBeforeTrigger(null))
				.isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testAfterTriggersFollowPriority() {
		Actionable actionable = mock(Actionable.class);
		Trigger lateTrigger = mock(Trigger.class);
		when(lateTrigger.test(actionable)).thenReturn(true);
		Trigger earlyTrigger = mock(Trigger.class);
		when(earlyTrigger.test(actionable)).thenReturn(true);
		Trigger anotherEarlyTrigger = mock(Trigger.class);

		Play earlyPlay = mock(Play.class);
		Play latePlay = mock(Play.class);
		when(mockPlayFactory.fromTrigger(null, earlyTrigger)).thenReturn(earlyPlay);
		when(mockPlayFactory.fromTrigger(null, lateTrigger)).thenReturn(latePlay);

		game.registerAfterTrigger(10, lateTrigger);
		game.registerAfterTrigger(earlyTrigger);
		game.registerAfterTrigger(anotherEarlyTrigger);
		assertThat(game.getAfterTriggerActionables(actionable)).hasSize(2).containsExactly(earlyPlay, latePlay);
		verify(earlyTrigger).test(actionable);
		verify(anotherEarlyTrigger).test(actionable);
		verify(lateTrigger).test(actionable);
		verifyNoMoreInteractions(earlyTrigger, anotherEarlyTrigger, lateTrigger);
	}

	@Test
	public void testAfterTriggerUniquelyAdded() {
		Trigger trigger = mock(Trigger.class);
		game.registerAfterTrigger(trigger);
		game.registerAfterTrigger(trigger);
		game.unregisterAfterTrigger(trigger);
		assertThat(game.getAfterTriggerActionables(null)).isEmpty();
		verifyNoInteractions(trigger);
	}

	@Test
	public void testAfterTriggerRemoveInvalid() {
		assertThatThrownBy(() -> game.unregisterAfterTrigger(null))
				.isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testFindPlayArea() {
		PlayArea<?,?> playArea = mock(PlayArea.class);
		BasePlayable playable = mock(BasePlayable.class);
		when(playArea.getAllPlayables()).thenReturn(Stream.of(playable));
		game.getPlayAreas().put("ID", playArea);

		assertThat(game.findPlayArea(playable)).isEqualTo(playArea);
	}

	@Test
	public void testFindPlayAreaPlayer() {
		PlayArea playerPlayArea = mock(PlayArea.class);
		BasePlayable playerPlayable = mock(BasePlayable.class);

		when(mockPlayer.getPlayAreas()).thenReturn(Stream.of(playerPlayArea));
		when(mockPlayer.getPlayArea("PLAYER")).thenReturn(playerPlayArea);
		when(playerPlayArea.getAllPlayables()).thenReturn(Stream.of(playerPlayable));

		assertThat(game.findPlayArea(playerPlayable)).isEqualTo(playerPlayArea);
	}

	@Test
	public void testFindPlayAreaNotFound() {
		assertThatThrownBy(() -> game.findPlayArea(mock(Playable.class)))
				.isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testFindPlayAreaByName() {
		PlayArea playArea = mock(PlayArea.class);

		when(mockPlayer.getPlayAreas()).thenReturn(Stream.of(playArea));
		when(mockPlayer.getPlayArea("PLAY_AREA")).thenReturn(playArea);
		when(playArea.getId()).thenReturn("PLAY_AREA");

		assertThat(game.findPlayArea("PLAY_AREA")).isSameAs(playArea);
	}

	@Test
	public void testFindPlayAreaByNameNotFound() {
		assertThatThrownBy(() -> game.findPlayArea("")).isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testFindPlay() {
		BasePlayable playable = mock(BasePlayable.class);
		Play play = mock(Play.class);

		when(mockPlayer.getAllPlayables()).thenReturn(Stream.of(playable));
		when(playable.getPlays()).thenReturn(List.of(play));
		when(play.getName()).thenReturn("PLAY");

		assertThat(game.findPlay(mockPlayer, "PLAY")).isSameAs(play);
	}

	@Test
	public void testFindPlayPlayerPlay() {
		Play play = mock(Play.class);

		when(mockPlayer.getPlays()).thenReturn(List.of(play));
		when(play.getName()).thenReturn("PLAY");

		assertThat(game.findPlay(mockPlayer, "PLAY")).isSameAs(play);
	}

	@Test
	public void testFindPlayNotFound() {
		assertThatThrownBy(() -> game.findPlay(mockPlayer, "")).isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testFindPlayablInPlayerPlayArea() {
		BasePlayable playable = mock(BasePlayable.class);

		when(mockPlayer.getAllPlayables()).thenReturn(Stream.of(playable));
		when(playable.getId()).thenReturn("PLAYABLE");

		assertThat(game.findPlayable("PLAYABLE")).isSameAs(playable);
	}

	@Test
	public void testFindPlayableGamePlayAreas() {
		PlayArea playArea = mock(PlayArea.class);
		BasePlayable playable = mock(BasePlayable.class);
		game.getPlayAreas().put("PLAY_AREA", playArea);

		when(playArea.getAllPlayables()).thenReturn(Stream.of(playable));
		when(playable.getId()).thenReturn("PLAYABLE");

		assertThat(game.findPlayable("PLAYABLE")).isSameAs(playable);
	}

	@Test
	public void testFindPlayableNotFound() {
		assertThatThrownBy(() -> game.findPlayable("")).isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testHasPlayable() {
		PlayArea playArea = mock(PlayArea.class);
		BasePlayable playable = mock(BasePlayable.class);

		when(mockPlayer.getPlayAreas()).thenReturn(Stream.of(playArea));
		when(playArea.getAllPlayables()).thenReturn(Stream.of(playable));
		when(playable.getId()).thenReturn("PLAYABLE");

		assertThat(game.hasPlayable("PLAYABLE")).isTrue();
	}

	@Test
	public void testHasPlayableNotFound() {
		assertThat(game.hasPlayable("INVALID")).isFalse();
	}

	@Test
	public void testFindPlayer() {
		when(mockPlayer.getId()).thenReturn("PLAYER");

		assertThat(game.findPlayer("PLAYER")).isSameAs(mockPlayer);
	}

	@Test
	public void testFindPlayerNotFound() {
		when(mockPlayer.getId()).thenReturn("PLAYER");
		when(mockOpponent.getId()).thenReturn("OPPONENT");
		assertThatThrownBy(() -> game.findPlayer("")).isExactlyInstanceOf(GameElementNotFoundException.class);
	}
}
