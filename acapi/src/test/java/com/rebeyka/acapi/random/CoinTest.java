package com.rebeyka.acapi.random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.rebeyka.acapi.exceptions.DieAlreadyRolledException;

public class CoinTest {

	@Test
	public void testCoinFlip() {
		Coin coin = DieBuilder.buildCoin();
		List<CoinSides> heads = new ArrayList<>();
		List<CoinSides> tails = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			coin.flip();
			if (coin.isHeads()) {
				heads.add(coin.getValue());
			}
			if (coin.isTails()) {
				tails.add(coin.getValue());
			}
			coin.reset();
		}
		assertThatThrownBy(() -> coin.flip().flip()).isInstanceOf(DieAlreadyRolledException.class);
		assertThat(heads.size()).isBetween(400, 600);
		assertThat(tails.size()).isBetween(400, 600);
		assertThat(heads.size() + tails.size()).isEqualTo(1000);
	}
}
