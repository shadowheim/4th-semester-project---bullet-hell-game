void keyPressed() {
  //---------------------------------------------------------------------/controls/-------------------------------------------
  if ( key == 'w') {
      Reimu.dirY = -1;
  } 
  if ( key == 'a') {
      Reimu.dirX = -1;
  } 
  if ( key == 's') {
      Reimu.dirY = 1;
  } 
  if ( key == 'd') {
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

void keyReleased() {
  //if ( (key == 'w' || key == 's' )  && !keyPressed) {
  //    Reimu.dirY = 0;
  //}

  //if ( (key == 'a' || key == 'd' ) && !keyPressed) {
  //    Reimu.dirX = 0;
  //}

  if ( key == 'w' || key == 's') {
    Reimu.dirY = 0;
  }

  if ( key == 'a' || key == 'd') {
    Reimu.dirX = 0;
  }

  if (key == CODED && keyCode == ALT) {
    Reimu.speed = 15;
  }
}
