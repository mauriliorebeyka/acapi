package com.rebeyka.acapi.entities;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.rebeyka.acapi.entities.gameflow.Play;
import com.rebeyka.acapi.exceptions.GameElementNotFoundException;

@SuppressWarnings({"rawtypes","unchecked"})
public class GameEntityFinderTest {

	private GameEntityFinder finder;
	
	@Mock
	private Game game;
	
	@Mock
	private Player mockPlayer;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);

		when(game.getPlayers()).thenReturn(List.of(mockPlayer));
		
		finder = new GameEntityFinder(game);
	}
	
	@Test
	public void testFindPlayArea() {
		PlayArea<?,?> playArea = mock(PlayArea.class);
		BasePlayable playable = mock(BasePlayable.class);
		when(playArea.contains(playable)).thenReturn(true);

		when(game.getPlayAreas()).thenReturn(Map.of("ID", playArea));
		
		assertThat(finder.playArea(playable)).isEqualTo(playArea);
	}
	
	@Test
	public void testFindPlayAreaPlayer() {
		PlayArea playerPlayArea = mock(PlayArea.class);
		BasePlayable playerPlayable = mock(BasePlayable.class);

		when(mockPlayer.getPlayAreas()).thenReturn(Stream.of(playerPlayArea));
		when(mockPlayer.getPlayArea("PLAYER")).thenReturn(playerPlayArea);
		when(playerPlayArea.contains(playerPlayable)).thenReturn(true);

		assertThat(finder.playArea(playerPlayable)).isEqualTo(playerPlayArea);
	}

	@Test
	public void testFindPlayAreaNotFound() {
		assertThatThrownBy(() -> finder.playArea(mock(BasePlayable.class)))
				.isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testFindPlayAreaByName() {
		PlayArea playArea = mock(PlayArea.class);
		PlayArea playArea2 = mock(PlayArea.class);

		when(mockPlayer.getPlayAreas()).thenReturn(Stream.of(playArea,playArea2));
		when(playArea.getId()).thenReturn("PLAY_AREA");

		assertThat(finder.playArea("PLAY_AREA")).isSameAs(playArea);
	}

	@Test
	public void testFindPlayAreaByNameNotFound() {
		assertThatThrownBy(() -> finder.playArea("")).isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testFindPlay() {
		Play play = mock(Play.class);

		when(mockPlayer.getAllPlays()).thenReturn(Stream.of(play));
		when(play.getName()).thenReturn("PLAY");

		assertThat(finder.play(mockPlayer, "PLAY")).isSameAs(play);
	}

	@Test
	public void testFindPlayNotFound() {
		assertThatThrownBy(() -> finder.play(mockPlayer, "")).isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testFindPlayablInPlayerPlayArea() {
		BasePlayable playable = mock(BasePlayable.class);

		when(mockPlayer.getAllPlayables()).thenReturn(Stream.of(playable));
		when(playable.getId()).thenReturn("PLAYABLE");

		assertThat(finder.playable("PLAYABLE")).isSameAs(playable);
	}

	@Test
	public void testFindPlayableGamePlayAreas() {
		PlayArea playArea = mock(PlayArea.class);
		BasePlayable playable = mock(BasePlayable.class);
		when(game.getPlayAreas()).thenReturn(Map.of("PLAY_AREA",playArea));

		when(playArea.getAllPlayables()).thenReturn(Stream.of(playable));
		when(playable.getId()).thenReturn("PLAYABLE");

		assertThat(finder.playable("PLAYABLE")).isSameAs(playable);
	}

	@Test
	public void testFindPlayableNotFound() {
		assertThatThrownBy(() -> finder.playable("")).isExactlyInstanceOf(GameElementNotFoundException.class);
	}

	@Test
	public void testHasPlayable() {
		BasePlayable playable = mock(BasePlayable.class);

		when(mockPlayer.getAllPlayables()).thenReturn(Stream.of(playable));
		when(playable.getId()).thenReturn("PLAYABLE");

		assertThat(finder.hasPlayable("PLAYABLE")).isTrue();
	}

	@Test
	public void testHasPlayableNotFound() {
		assertThat(finder.hasPlayable("INVALID")).isFalse();
	}

	@Test
	public void testFindPlayer() {
		when(mockPlayer.getId()).thenReturn("PLAYER");

		assertThat(finder.player("PLAYER")).isSameAs(mockPlayer);
	}

	@Test
	public void testFindPlayerNotFound() {
		when(mockPlayer.getId()).thenReturn("PLAYER");
		assertThatThrownBy(() -> finder.player("")).isExactlyInstanceOf(GameElementNotFoundException.class);
	}
	
}
