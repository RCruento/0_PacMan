package luncher;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;

import ecrans.AcceuilEcran;

public class Luncher extends Game implements ApplicationListener {

    @Override
    public void create () {
        setScreen(new AcceuilEcran(this));
    }
}