package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rebeyka.acapi.builders.PlayFactory;
import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.entities.gameflow.Ranking;
import com.rebeyka.acapi.entities.gameflow.Timeline;

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
		
		game = new Game("TEST",List.of(mockPlayer,mockOpponent));
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
		verify(mockTimeline,times(3)).hasNext();
		verify(mockTimeline).executeNext();
		verify(mockTimeline).queue(queuedPlay1);
		verify(queuedPlay1).isPossible();
		verify(queuedPlay2).isPossible();
		verify(ranking,times(2)).updateRanking(List.of(mockPlayer,mockOpponent));
		verifyNoMoreInteractions(mockTimeline,queuedPlay1,queuedPlay2,ranking);
		
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
		when(mockTimeline.executeNext()).thenReturn(true,false);
		assertThat(game.executeAll()).isTrue();
		verify(mockTimeline,times(2)).executeNext();
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
}
