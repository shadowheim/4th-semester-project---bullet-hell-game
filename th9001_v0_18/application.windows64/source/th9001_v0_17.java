import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class th9001_v0_17 extends PApplet {

//astroids oop
PImage img1, img2, img3, img4, easymode1, bullets;
PFont font;


Minim minim;
AudioPlayer easymodeAudio;

int NumberOfAsteroids = 60;

// state = 0 Main Menu
// state = 1 main Menu 2
// state = 2 instructions

// state = 3 reset Game
// state = 4 starts game
// state = 5 game over
// state = 6 win game
// State = 7 easymode

int state = 0;
int gameMode = 0;
ArrayList<Asteroid> asteroidList = new ArrayList<Asteroid>();





//Asteroid a1 = new Asteroid();
int is = 44;


//Ship spaceShip = new Ship();
Reimu Reimu = new Reimu();
Timer timer = new Timer();

public void setup() {
       
    img1 = loadImage("img1.jpg");
    img1.resize(width, height);
    img2 = loadImage("reimu2.jpg");
    img3 = loadImage("instructions.png");
    img4 = loadImage("9shrug.png");

    bullets = loadImage("bullets2.png");
    bullets.resize(1598, 782);

    Minim minim = new Minim(this);
    easymodeAudio = minim.loadFile("eeeh-easy-mode1.mp3");
    easymode1 = loadImage("ehreallyeasymode.png");
    easymode1.resize(960, 720);



    font = createFont("FreePixel.ttf", 60);
    textFont(font);



    Reimu.init();

    for (int i = 0; i < NumberOfAsteroids; i++ ) {
        Asteroid tempAsteroid = new Asteroid();
        tempAsteroid.init(bullets);

        asteroidList.add( tempAsteroid );
    }
}

public void draw() {
    frameRate(60);
    //-----------------------------------------------------------------/STATE 0( MAIN MENU )/-------------------------------------------------------------------
    if (state == 0) {
        gameSetup();
        mainMenu();
        //if (keyPressed && key == CODED) {
        //    if (keyCode == ENTER){
        //    state = 1;
        //    timer.reset();
        //    }
        //}



        if (keyPressed && key == 'y') { //-------------------------------------( MENU 2 )---------------------------------------------------
            state = 1; // goes to menu 2
        }  
        if (keyPressed && key == 'u') { // goes to instructions
            state = 2;
        }
        //-----------------------------------------------------------------/STATE 1( MAIN MENU 2 )/-------------------------------------------------------------------
    } else if (state == 1) { 
        mainMenu2();
        easymodeAudio.pause();
        if (keyPressed && key == '1') { //-------------------------------------( easy mode )--------------------------------------------------------
            gameMode = 0;
            state = 3;
        }

        if (keyPressed && key == '2') { //-------------------------------------( normal mode )----------------------------------------------------
            gameMode = 1;
            state = 3;
        }


        if (keyPressed && key == '3') { //--------------------------------------( hard mode )----------------------------------------------------
            gameMode = 2;
            state = 3;
            //timer.reset();
        } 

        if (keyPressed && key == '4') { //-------------------------------------( lunatic mode )---------------------------------------------------
            gameMode = 3;
            state = 3;
        }

        //-----------------------------------------------------------------/STATE 2( Instructions )/-------------------------------------------------------------------
    } else if (state == 2) {
        gameInstructions();
        if (keyPressed && key == 'p') {
            state = 0; // goto menu 1
        }
    }


    //}

    //--------------------------------------------------------------/STATE 3&4( MAIN GAME )/------------------------------------------------------------------
    else if (state == 3) {
        if (gameMode == 0) {
            NumberOfAsteroids = 15;
        } else if (gameMode == 1) {
            NumberOfAsteroids = 30;
        } else if (gameMode == 2) {
            NumberOfAsteroids = 50;
        } else if (gameMode == 3) {
            NumberOfAsteroids = 65;
        }

        gameSetup();
        timer.reset();
        state = 4;
    } else if (state == 4) {
        if (gameMode == 0) {
            easymodeBackground();
            easymodeAudio.play();
            println("easymodeAudio");
            if ( easymodeAudio.position() == easymodeAudio.length() )
            {
                easymodeAudio.rewind();
                easymodeAudio.play();
            }
        } else {
            standardBackground();
        }
        mainGame();
        timer.update();
        if (timer.timeIsOff()) { // Game win
            state = 6;
        }
        if (Reimu.HP <= 0) { // Game over
            state = 5;
        }

        //-------------------------------------------------------------/STATE 5( GAME OVER )/-----------------------------------------------------------------------
    } else if (state == 5) {
        gameOver();
        easymodeAudio.pause();
        println("easymodeAudio.pause");

        if (keyPressed && key == 'g') { //---------------------------------starts game
            state = 3;
        }
        if (keyPressed && key == 'l') { //---------------------------------returns to choose levels
            state = 1;
        }

        //-------------------------------------------------------------/STATE 3( GAME WIN )/------------------------------------------------------------------------
    } else if (state == 6) {
        gameWin();
        easymodeAudio.pause();
        if (keyPressed && key == 'g') { //---------------------------------starts game
            state = 3;
        }
        if (keyPressed && key == 'l') { //---------------------------------returns to choose levels
            state = 1;
        }        

        //-----------------------------------------------------------------/STATE 4( INSTRUCTIONS )/--------------------------------------------------------------------
    }
}


public void mainMenu() {
    background(255);
    textAlign(CENTER);
    fill(0);
    textSize(100);
    text("TOTALLY NOT A TOUHOU RIP OFF", width/2, height/2);
    fill(0);
    textSize(50);
    text("PRESS Y TO START", width/2, height/2 + 200);
    //-----------------------------------------------------------------/instructions/----------------------------------------------------
    fill(150, 100, 100);
    textSize(80);
    text("PRESS U FOR INSTRUCTIONS", width/2, height/2 + 400);
}

public void mainMenu2() {
    background(255);
    textAlign(CENTER);
    fill(0);
    textSize(100);
    text("TOTALLY NOT A TOUHOU RIP OFF", width/2, height/2);
    //-----------------------------------------------------------------/choose difficulty/----------------------------------------------------
    textSize(40);
    fill(0, 160, 0);
    text("PRESS 1: Easy", width/2, height/2 + 70);
    fill(0, 0, 255);
    text("PRESS 2: Normal", width/2, height/2 + 120);
    fill(0xffFFBF00);
    text("PRESS 3: Hard", width/2, height/2 + 170);
    fill(255, 0, 0);
    text("PRESS 4: Lunatic", width/2, height/2 + 220);
}


public void mainGame() {
    textSize(100);
    fill(50);
    text(Reimu.HP, 150, 150);

    timer.display();
    Reimu.move();
    Reimu.update();

    for (int i = 0; i < asteroidList.size(); i++) {
        Asteroid tempAsteroid = asteroidList.get( i );
        tempAsteroid.update();
        //tempAsteroid.move();
        tempAsteroid.animateBullets();
        //tempAsteroid.toPrint();

        //check of collision with ship

        if ( dist(tempAsteroid.x, tempAsteroid.y, Reimu.x, Reimu.y) < tempAsteroid.sizeY / 2 + Reimu.sizeY / 6) {
            println(" an asteroid hit ");
            //minus 1 HP
            Reimu.HP--;
            //removes 1 asteroid from the list
            asteroidList.remove( i );
        }
        if (Reimu.HP <= 0) {
            textSize(100);
            textAlign(CENTER);
            fill(0, 100, 100);
            text("you lose", width/2, height/2);
        }
    }
}


public void easymodeBackground() {
    background(255);
    imageMode(CENTER);
    image(easymode1, width/2, height/2);
}

public void standardBackground() {
    imageMode(CENTER);
    image(img1, width/2, height/2);
}

public void gameWin() {
    background(255);
    imageMode(CENTER);
    img2.resize(480 * 2, 360 * 2);
    image(img2, width/2, height/2);
    textAlign(CENTER);
    textSize(100);
    fill(random(255), random(255), random(255));
    text("CLEAR!", width/2, 160);
    fill(0);
    textSize(70);
    text("PRESS G TO RESTART", width/2, height/2 + 500);
}

public void gameOver() {
    background(255);
    imageMode(CENTER);
    image(img4, width/2, height/2);
    textAlign(CENTER);
    textSize(100);
    fill(random(255), random(255), random(255));
    text("GAME OVER", width/2, height/2);
    fill(0);
    textAlign(CENTER);
    stroke(100);
    textSize(100);
    text("PRESS G TO RESTART", width/2, height/2 + 240);
    textSize(100);
    text("PRESS L TO RETURN TO DIFFICULTY SELECT", width/2, height/2 + 170);
}

public void gameInstructions() {
    background(255);
    imageMode(CENTER);
    image(img3, width/2, height/2);
}

public void gameSetup() {

    Reimu.init();
    asteroidList.clear();
    for (int i = 0; i < NumberOfAsteroids; i++ ) {
        Asteroid tempAsteroid = new Asteroid();
        // size size : 0 empty
        tempAsteroid.init(bullets);
        // size : 20 - 50

        asteroidList.add( tempAsteroid );
    }
}
public void mousePressed() {
    if (state == 1) {
        state = 0;
    }
}
class Timer { //<>//
    int begin;
    int duration;
    int time;

    public void reset() {

        begin = millis();
        time = duration = 30;
    }


    public void update() {
        if (time > 0) {
            time = duration - (millis() - begin)/1000;
        }
    }

    public void display() {  
        textSize(120);
        textAlign(CENTER);
        fill(255);
        if (gameMode == 0) {
            fill(0);
        }
        text(time, width/2, 100);
    }

    public boolean timeIsOff() {
        if (time == 0) return true;
        else return false;
    }
}
class Asteroid {

    PImage bullets;


    float x;
    float y;

    int w = 70; // width of bluebullet
    int h = 44; // hieght of blue bullet

    int xSprite; // (int)x for blue bullet
    int ySprite; // (int)y for blue bullet

    float counter = 0;
    float counterLeft = 0;


    float dirX, dirY;

    float speedX;

    float speedY;
    
    int sizeX;
    int sizeY;
   


    public void init(PImage bullets) {
        //size = random(20, 100);
        sizeX = 105;
        sizeY = 49;
        
        
        this.bullets = bullets; 

        if (random(0, 100) > 50) {
            x = random(0, width/2 - 100);
            //x er rando 0 til width/2-20
        } else {
            x = random(width/2 + 100, width);
            //x er random width/2+20 til width
        }

        if (random (0, 100) > 50 ) {
            y = random(height/2 - 100);
        } else {
            y = random(height/2 + 100, height);
        }


        //x = random(0, );
        //y = random(0, height);




        speedX = random(1, 8);
        speedY = random(1, 8);

        if (gameMode == 0) { // speed manipulator for easy mode
            speedX = random(1, 4);
            speedY = random(1, 4);
        }

        if (gameMode == 1) { // speed manipulator for normal mode
            speedX = random(1, 6);
            speedY = random(1, 6);
        }

        if (gameMode == 2) { // speed manipulator for hard mode
            speedX = random(1, 8);
            speedY = random(1, 8);
        }

        if (gameMode == 3) { // speed manipulator for lunatic mode
            speedX = random(1, 9);
            speedY = random(1, 9);
        }

        dirX = 1;
        dirY = 1;

        if ( random(0, 1) > 0.5f) {
            dirX = -1;
        }
        if ( random (0, 1) > 0.5f) {
            dirY = -1;
        }
    }

    //----------------------------------------------------------------------------- stuff that makes the asteroids move
    public void update() {
        x = x + dirX * speedX;
        y = y + dirY * speedY;

        if (y > height) {
            y = 0;
        }
        if (y < 0) {
            y = height;
        }
        if (x > width) {
            x = 0;
        }
        if (x < 0) {
            x = width;
        }
    }
    //----------------------------------------------------------------------------- stuff that spawns the asteroids
    public void animateBullets() {
        //fill(255, 255, 255);
        //ellipse(x, y, size, size);
        //ellipse(x, y, sizeY, sizeY);
        
        ySprite = ((int)counter % 4) * h;
        
        //pushMatrix()

        copy(bullets, xSprite+422, ySprite+231, w, h, (int)x-w/2, (int)y-h/2, w, h);
        counter = counter + 0.5f;
        if (counter == 4) {
            counter = 0;
        }
    }

}
public void keyPressed() {
    //---------------------------------------------------------------------/controls/-------------------------------------------
    if ( key == 'w' ) {
        Reimu.dirY = -1;
    } 
    if ( key == 'a' ) {
        Reimu.dirX = -1;
    } 
    if ( key == 's' ) {
        Reimu.dirY = 1;
    } 
    if ( key == 'd' ) {
        Reimu.dirX = 1;
    }    
    //--------------------------------------------------------------------/abilities/--------------------------------------------------------------------
    if (key == CODED && keyCode == ALT) {
        Reimu.speed = 7;
    }
    if (key == 'r') {
        state = 1;
    }
}

public void keyReleased() {
    if ( key == 'w' || key == 's' ) {
        Reimu.dirY = 0;
    }

    if ( key == 'a' || key == 'd' ) {
        Reimu.dirX = 0;
    }

    if (key == CODED && keyCode == ALT) {
        Reimu.speed = 15;
    }
}


class Ship{
    float x;
    float y;
    
    float speed;
    
    float dirX;
    float dirY;
    
    float size;
    
    int  HP;
    
    
    public void init() {
        
     HP = 10;   
     speed = 20;   
     size = 60;
     
     x = width / 2;
     y = height / 2;
    }
    
    
    public void update() {
        // border restriction
        if(x < 20){
            x = 20;
        }
        
        if(y < 20){
            y = 20;
        }
       
        if(x > width-20){
            x = width-20;
        }
        
        if(y > height-20){
            y = height-20;
        }
        
    }
    public void move() {
        x = x + dirX * speed;
        y = y + dirY * speed;
        

        
    }
    
    public void spawn() {
        rectMode(CENTER);
        fill(120);
        rect(x, y, size-20, size-20);


        noFill();
        stroke(200);
        ellipse(x, y, size, size);
    }
        
}
class Reimu {

    PImage reimuSpriteSheet;
    // dimensions for each sprite
    int w = 96;
    int h = 150;
    float counter = 0;
    float counterLeft = 0;
    int xSprite;
    int ySprite;

    float x;
    float y;

    int speed;

    float sizeX;
    float sizeY;

    float dirX;
    float dirY;

    int HP;


    public void init() {

        HP = 3000;

        if (gameMode == 3) {
            HP = 1;
        }

        speed = 15;
        x = width/2;
        y = height/2;

        sizeX = 96;
        sizeY = 150;

        loadReimuSprite();
    }

    public void loadReimuSprite() {
        //size(400, 400);
        reimuSpriteSheet = loadImage("reimu_sprite_sheet.png");
        reimuSpriteSheet.resize(969, 774);
    }

    public void animateReimu(int yOffset, int xOffset) {
        //-----------------------------------------------------------------------/Reimu sprite animation/--------------------------------------------------------------------
        xSprite = ((int)counter % 8) * w;
        //ellipse(x, y, sizeY/3, sizeY/3);

        copy(reimuSpriteSheet, xSprite + xOffset, ySprite + yOffset, w, h, (int)x-w/2, (int)y-h/2, w, h);
        counter = counter + 0.5f;

        //if (counter == 8) {
        //    counter = 0;
        //}
    }
    public void update() {
        // border restriction
        if (x < 48) {
            x = 48;
        }

        if (y < 75) {
            y = 75;
        }

        if (x > width-48) {
            x = width-48;
        }

        if (y > height-75) {
            y = height-75;
        }
        //-------------------------------------------------------------------------/ sprite animation for strife left and right/-----------------------------------------------


        if (keyPressed && key == 'a') { // a
            animateReimu(150, 3);
            if (counter == 8) {
                counter = 5;
            }
        } else if (keyPressed && key == 'd') { // d
            animateReimu(285, 3);
            if (counter == 8) {
                counter = 5;
            }
        } else {
            animateReimu(0, 3);
            if (counter == 8) {
                counter = 0;
            }
        }
    }

    public void move() {
        x = x + dirX * speed;
        y = y + dirY * speed;
    }
}
    public void settings() {  size(1920, 1080, P2D); }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[] { "th9001_v0_17" };
        if (passedArgs != null) {
          PApplet.main(concat(appletArgs, passedArgs));
        } else {
          PApplet.main(appletArgs);
        }
    }
}
