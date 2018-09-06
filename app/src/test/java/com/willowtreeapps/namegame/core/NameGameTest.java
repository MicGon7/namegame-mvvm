package com.willowtreeapps.namegame.core;

import com.willowtreeapps.namegame.NameGame;
import com.willowtreeapps.namegame.model.ProfilesRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

public class NameGameTest {
    @Mock
    ProfilesRepository mProfilesRepository;

    private NameGame mNameGame;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
        mNameGame = new NameGame(mProfilesRepository);
    }

    @Test
    public void testDataLoad() {
        //TestData testData = new TestData(mApplication, mNameGameApi);
        mNameGame.loadData();
        verify(mProfilesRepository).load();

    }
}