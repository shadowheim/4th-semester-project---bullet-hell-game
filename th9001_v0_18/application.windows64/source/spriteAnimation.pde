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


    void init() {

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

    void loadReimuSprite() {
        //size(400, 400);
        reimuSpriteSheet = loadImage("reimu_sprite_sheet.png");
        reimuSpriteSheet.resize(969, 774);
    }

    void animateReimu(int yOffset, int xOffset) {
        //-----------------------------------------------------------------------/Reimu sprite animation/--------------------------------------------------------------------
        xSprite = ((int)counter % 8) * w;
        //ellipse(x, y, sizeY/3, sizeY/3);

        copy(reimuSpriteSheet, xSprite + xOffset, ySprite + yOffset, w, h, (int)x-w/2, (int)y-h/2, w, h);
        counter = counter + 0.5;

        //if (counter == 8) {
        //    counter = 0;
        //}
    }
    void update() {
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

    void move() {
        x = x + dirX * speed;
        y = y + dirY * speed;
    }
}
