#include <Polygon.hpp>
#include <GL/freeglut.h>
#include <GL/gl.h>
#include <GL/glu.h>
#include <fstream>
#include <iostream>

Polygon p;
Point point;
GLdouble left = 0;
GLdouble right = 10;
GLdouble top = 0;
GLdouble bottom = -10;

void display() 
{
	glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // Set background color to black and opaque
	glClear(GL_COLOR_BUFFER_BIT);         // Clear the color buffer (background) 
 
    GLdouble coords[]={0,0};
	glBegin(GL_LINE_STRIP);      
      glColor3f(0.0f, 0.0f, 0.5f);
	  
      for(Point point: p.points){
          coords[0] = point.x;
          coords[1] = -point.y;
          glVertex2dv(coords);
      }
      coords[0] = p.points[0].x;
      coords[1] = -p.points[0].y;
      glVertex2dv(coords); 
	glEnd();

    glBegin(GL_POLYGON); 
        glColor3f(0.7f, 0.0f, 0.0f);        
        glVertex2d(point.x+0.1d,-point.y);
        glVertex2d(point.x,-point.y+0.1d);
        glVertex2d(point.x-0.1d,-point.y);
        glVertex2d(point.x,-point.y-0.1d);
    glEnd();

    glBegin(GL_LINE_STRIP);
    glColor3f(0.0f, 0.7f, 0.0f);  
    glVertex2d(left,-point.y);
    glVertex2d(right,-point.y);
    glEnd();

    double divisionX=1.;
    double divisionY=1.;

    ///Draw OX
    glBegin(GL_LINE_STRIP);
        glColor3f(0.0f, 0.0f, 0.5f);
        coords[0]=left;
        coords[1]=0.;
        glVertex2dv(coords);
        coords[0]=right;
        glVertex2dv(coords);
    glEnd();

    ///Draw OY
    glBegin(GL_LINE_STRIP);
        glColor3d(0.0f, 0.0f, 0.5f);
        coords[0]=0;
        coords[1]=bottom;
        glVertex2dv(coords);
        coords[1]=top;
        glVertex2dv(coords);
    glEnd();

    ///Draw marks on OX ans OY
    for(long double ptr=0.+divisionX;ptr<=right;ptr+=divisionX){
            coords[0]=ptr;
            coords[1]=divisionY/5;

            glBegin(GL_LINE_STRIP);
            glColor3f(0.0f, 0.0f, 0.5f);
            coords[0]=ptr;
            coords[1]=divisionY/5;
            glVertex2dv(coords);
            coords[1]=-divisionY/5;
            glVertex2dv(coords);
            glEnd();

            coords[0]=-ptr;
            coords[1]=divisionY/5;
            glBegin(GL_LINE_STRIP);
            glColor3f(0.0f, 0.0f, 0.5f);
            coords[0]=-ptr;
            coords[1]=divisionY/5;
            glVertex2dv(coords);
            coords[1]=-divisionY/5;
            glVertex2dv(coords);
            glEnd();
        }

        for(long double ptr=0.-divisionY;ptr>=bottom;ptr-=divisionY){
            coords[0]=divisionX/5;
            coords[1]=-ptr;

            glBegin(GL_LINE_STRIP);
            glColor3f(0.0f, 0.0f, 0.5f);
            coords[0]=divisionX/5;
            coords[1]=ptr;
            glVertex2dv(coords);
            coords[0]=-divisionX/5;
            glVertex2dv(coords);
            glEnd();

            coords[0]=divisionX/5;
            coords[1]=ptr;

            glBegin(GL_LINE_STRIP);
            glColor3f(0.0f, 0.0f, 0.5f);
            coords[0]=divisionX/5;
            coords[1]=-ptr;
            glVertex2dv(coords);
            coords[0]=-divisionX/5;
            glVertex2dv(coords);
            glEnd();
        }

	glFlush();  // Render now
}


void reshape(GLsizei width, GLsizei height) {
   glViewport(0, 0, width, height);

   glMatrixMode(GL_PROJECTION);  
   glLoadIdentity();            
      gluOrtho2D(left,right,top,bottom);
}

Polygon getPolygon(){
    std::ifstream file;
    file.open("input.txt");

    file>>point.x;
    file>>point.y;

    int count;
    file >> count;

    std::vector<Point> points;
    for(int i = 0; i<count; ++i){
        double x, y;
        file >> x;
        file >> y;
        points.push_back(Point(x,y));
    }

    file.close();

    return Polygon(points);
}

int main(int argc, char** argv){    
    p = getPolygon();
    std::cout<< std::boolalpha << p.belongs(point)<<std::endl;

    glutInit(&argc, argv);
	glutCreateWindow("Polygon and Dot"); 
	glutInitWindowSize(500, 500);   
	glutInitWindowPosition(1000, 1000);
	glutDisplayFunc(display);		
	glutReshapeFunc(reshape);
	glutDisplayFunc(display);		
	glutMainLoop();
    
    return 0;
}