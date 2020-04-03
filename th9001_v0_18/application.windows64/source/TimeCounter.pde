class Timer { //<>//
    int begin;
    int duration;
    int time;

    void reset() {

        begin = millis();
        time = duration = 30;
    }


    void update() {
        if (time > 0) {
            time = duration - (millis() - begin)/1000;
        }
    }

    void display() {  
        textSize(120);
        textAlign(CENTER);
        fill(255);
        if (gameMode == 0) {
            fill(0);
        }
        text(time, width/2, 100);
    }

    boolean timeIsOff() {
        if (time == 0) return true;
        else return false;
    }
}
