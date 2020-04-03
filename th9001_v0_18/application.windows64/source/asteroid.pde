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
   


    void init(PImage bullets) {
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

        if ( random(0, 1) > 0.5) {
            dirX = -1;
        }
        if ( random (0, 1) > 0.5) {
            dirY = -1;
        }
    }

    //----------------------------------------------------------------------------- stuff that makes the asteroids move
    void update() {
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
    void animateBullets() {
        //fill(255, 255, 255);
        //ellipse(x, y, size, size);
        //ellipse(x, y, sizeY, sizeY);
        
        ySprite = ((int)counter % 4) * h;
        
        //pushMatrix()

        copy(bullets, xSprite+422, ySprite+231, w, h, (int)x-w/2, (int)y-h/2, w, h);
        counter = counter + 0.5;
        if (counter == 4) {
            counter = 0;
        }
    }

}
