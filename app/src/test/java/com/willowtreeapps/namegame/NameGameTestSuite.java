package com.willowtreeapps.namegame;

import com.willowtreeapps.namegame.core.NameGameTest;
import com.willowtreeapps.namegame.network.api.ProfileTest;
import com.willowtreeapps.namegame.viewmodel.NameGameViewModelTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        NameGameViewModelTest.class,
        NameGameTest.class,
        ProfileTest.class
})

public class NameGameTestSuite {
}
