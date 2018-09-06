package com.willowtreeapps.namegame.viewmodel;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.willowtreeapps.namegame.NameGame;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NameGameViewModelTest {


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

/*   @liveDataRule: This bypasses the main thread check,
     and immediately runs any tasks on your test thread,
     allowing for immediate and predictable calls and therefore assertions.
     https://medium.com/pxhouse/unit-testing-with-mutablelivedata-22b3283a7819*/

    @Rule
    public TestRule liveDataRule = new InstantTaskExecutorRule();

    private NameGameViewModel mNameGameViewModel;

    @Mock
    private NameGame mNameGame;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mNameGameViewModel = new NameGameViewModel(mNameGame);

    }

    @Test
    public void currentRoundAsRound() {
        when(mNameGameViewModel.getCurrentRound()).thenReturn(1);
        assertEquals(1, mNameGame.getRound());
    }

    @Test
    public void roundIncrement() {
        when(mNameGameViewModel.getCurrentRound()).thenAnswer(new Answer<Integer>() {
            private int count;

            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return ++count;
            }
        });
        assertEquals(1, mNameGame.getRound());
        assertEquals(2, mNameGame.getRound());
        assertEquals(3, mNameGame.getRound());
    }

    @Test
    public void gameOverCondition() {
        when(mNameGameViewModel.isGameOver()).thenReturn(true);
        assertEquals(true, mNameGame.isGameOver());
    }

    @Test
    public void callLoadData() {
        mNameGameViewModel.loadData();
        verify(mNameGame).loadData();
    }

    @Test
    public void callStartGame() {
        mNameGameViewModel.startGame();
        verify(mNameGame).startGame();
    }

    @Test
    public void callOnProfileSelected() {
        mNameGameViewModel.onProfileSelected(1);
        verify(mNameGame).onProfileSelected(1);
    }

    @Test
    public void callResetGame() {
        mNameGameViewModel.resetGame();
        verify(mNameGame).resetGame();
    }
}