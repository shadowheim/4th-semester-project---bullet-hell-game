

class Ship{
    float x;
    float y;
    
    float speed;
    
    float dirX;
    float dirY;
    
    float size;
    
    int  HP;
    
    
    void init() {
        
     HP = 10;   
     speed = 20;   
     size = 60;
     
     x = width / 2;
     y = height / 2;
    }
    
    
    void update() {
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
    void move() {
        x = x + dirX * speed;
        y = y + dirY * speed;
        

        
    }
    
    void spawn() {
        rectMode(CENTER);
        fill(120);
        rect(x, y, size-20, size-20);


        noFill();
        stroke(200);
        ellipse(x, y, size, size);
    }
        
}
