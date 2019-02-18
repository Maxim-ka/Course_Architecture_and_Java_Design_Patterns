interface Hostile{
    void attack();                 // поведение обьектов
    void setActive(boolean active);
    boolean isActive();
}

class Zombie implements Hostile{  //реализация
    private boolean active;

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void attack() {  //реализация
        setActive(false); // убили
    }
}

class Shahid implements Hostile{   //реализация
    private boolean active;

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void attack() { //реализация
        setActive(false); //убили
     }
}

class Hooligan implements Hostile{ //реализация
    private boolean active;

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void attack() {  //реализация
        setActive(false); // убили
    }
}

abstract class Eater{
    boolean active;
    TextureRegion region;          //картинка в спрайте

    void setActive(boolean active) {
        this.active = active;
    }
}

class Enemy extends Eater{          //абстракция;

    Hostile hostile;

    void init(Hostile hostile){    //обьекты реализации будут менятся в игре
        this.hostile = hostile;
        hostile.setActive(true);
    }

    void hit(){
        hostile.attack();
        setActive(hostile.isActive());
        if (!active) hostile = null;
    }
}

class Main{

    int levelGame;
    Enemy enemy;
    TextureRegion[] textureRegions; // картинки


    void init(){
        switch (levelGame){
            case 0:
                enemy.init(new Zombie());
                enemy.region = textureRegions[0];
                break;
            case 1:
                enemy.init(new Shahid());
                enemy.region = textureRegions[1];
                break;
            case 2:
                enemy.init(new Hooligan());
                enemy.region = textureRegions[2];
                break;
        }
    }

    void game(){
        enemy.hit();
    }
}

