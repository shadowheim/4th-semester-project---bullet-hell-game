//astroids oop
PImage img1, img2, img3, img4, easymode1, bullets;
PFont font;

import ddf.minim.*;
Minim minim;
AudioPlayer easymodeAudio;

int NumberOfAsteroids = 60;

// state = 0 Main Menu
// state = 1 main Menu
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

void setup() {
    size(1920, 1080, P2D);   
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

void draw() {

    
    //if (!keyPressed) {
    //    Reimu.dirY = 0;
    //    Reimu.dirX = 0;
        
    //}

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
            NumberOfAsteroids = 35;
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


void mainMenu() {
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

void mainMenu2() {
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
    fill(#FFBF00);
    text("PRESS 3: Hard", width/2, height/2 + 170);
    fill(255, 0, 0);
    text("PRESS 4: Lunatic", width/2, height/2 + 220);
}


void mainGame() {
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

        if ( dist(tempAsteroid.x, tempAsteroid.y, Reimu.x, Reimu.y) < tempAsteroid.sizeY / 2 + Reimu.sizeY / 2 || dist(tempAsteroid.x, tempAsteroid.y, Reimu.x, Reimu.y) <  tempAsteroid.sizeX / 2 + Reimu.sizeX / 2) {
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


void easymodeBackground() {
    background(255);
    imageMode(CENTER);
    image(easymode1, width/2, height/2);
}

void standardBackground() {
    imageMode(CENTER);
    image(img1, width/2, height/2);
}

void gameWin() {
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

void gameOver() {
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

void gameInstructions() {
    background(255);
    imageMode(CENTER);
    image(img3, width/2, height/2);
}

void gameSetup() {

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
void mousePressed() {
    if (state == 1) {
        state = 0;
    }
}
