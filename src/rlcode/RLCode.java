package rlcode;
/*
Codificacion de imagenes usando Run-length encoding
se usa el caracter x para separar la cantidad de repeticiones y el valor del color
*/
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import javax.imageio.ImageIO;


public class RLCode {

     public static void main(String[] args) {
        try {
            //solo el nombre sin extension imagenes png unicamente
            Scanner entrada = new Scanner(System.in); 
            System.out.println("Nombre de la imagen: ");
            String nombre=entrada.nextLine();
            //se lee la imagen
            BufferedImage imagen=ImageIO.read(new File(nombre+".png"));
            //se codifica y se guarda el resultado en una lista matriz de Strings
            LinkedList<LinkedList<String>> respuesta=codificar(imagen);
            System.out.println("Codificacion: ");
            int tamano=0;
            //imprime codificacion, cada caracter pesa un byte, por lo que el tamaño es la cantidad de caracteres en la codificacion
            for (int i=0;i<respuesta.size();i++){
                for(int j=0;j<respuesta.get(i).size();j++){
                    tamano=tamano+respuesta.get(i).get(j).length();
                    System.out.print(respuesta.get(i).get(j)+ " ");
                }
            }
            System.out.println(" ");
            //cada pixel guarda 3 enteros para el color, r,g,b, y cada entero pesa 4 bytes, por lo que el tamaño
            //esel numbero de pixeles multiplicado por 12
            System.out.println("Tamaño sin codificar codificacion: "+imagen.getWidth()*imagen.getHeight()*3*4 + " bytes");
            System.out.println("Tamaño codificada: "+tamano+ " bytes");
            //se decodifica y se guarda en una imagen 'nombre'Out.png
            BufferedImage img = Decodificar( respuesta,imagen.getWidth(),imagen.getHeight());
            ImageIO.write(img, "png", new File(nombre+"Out.png"));
            //si se quiere ver la codificacion de la imagen de salida y comparar que es igual a la codificacion de la imagen original
            //System.out.println(" ");
            /*LinkedList<LinkedList<String>> respuesta2=codificar(ImageIO.read(new File("pruebae.jpg")));
            for (int i=0;i<respuesta2.size();i++){
                for(int j=0;j<respuesta2.get(i).size();j++){
                    System.out.print(respuesta2.get(i).get(j)+ " ");
                }
                System.out.println(" ");
            }*/
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
     //metodo para codificar, recibe la imagen a codificar
    private static LinkedList<LinkedList<String>> codificar(BufferedImage image) {
        LinkedList<LinkedList<String>> codificacion = new LinkedList<LinkedList<String>>();
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();
        LinkedList<String> codifR = new LinkedList<String>();
        //se recorre cada pixel de la imagen, primero para la matriz red
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                //la informacion del color rojo esta en la posicion 0 representando el pixel como un arreglo
                int color1R=pixels[0];
                int color2R=pixels[0];
                int contadorIgualesR=0;
                //se guarda la cantidad de pixeles contiguos con el mismo color
                while (color2R==color1R){
                    contadorIgualesR=contadorIgualesR+1;
                    if (yy<height-1){
                        yy=yy+1;
                        pixels = raster.getPixel(xx, yy, (int[]) null);
                        color2R=pixels[0];
                    }else{
                        break;
                    }  
                }
                //se guarda una posicion de la codificacion en una lista temporal
                if (contadorIgualesR>1){
                    String salida=contadorIgualesR +"x"+color1R;
                    codifR.add(salida);
                }
                //si un pixel no tiene el mismo color que sus vecinos su codificacion permanece igual
                else{
                    String salida = ""+ color1R;
                    codifR.add(salida);
                }
                if (color1R!=color2R){
                   yy=yy-1;
                }
               }
        }
        //de forma analoga se hace con las matrices green y blue
        LinkedList<String> codifG = new LinkedList<String>();
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                int color1G=pixels[1];
                int color2G=pixels[1];
                int contadorIgualesG=0;
                while (color2G==color1G){
                    contadorIgualesG=contadorIgualesG+1;
                    if (yy<height-1){
                        yy=yy+1;
                        pixels = raster.getPixel(xx, yy, (int[]) null);
                        color2G=pixels[1];
                    }else{
                        break;
                    }  
                }
             
                if (contadorIgualesG>1){
                    String salida=contadorIgualesG +"x"+color1G;
                    codifG.add(salida);
                }
                else{
                    String salida = ""+ color1G;
                    codifG.add(salida);
                }
                if (color1G!=color2G){
                   yy=yy-1;
                }  
            }
        }
        LinkedList<String> codifB = new LinkedList<String>();
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                int color1B=pixels[2];
                int color2B=pixels[2];
                int contadorIgualesB=0;
                while (color2B==color1B){
                    contadorIgualesB=contadorIgualesB+1;
                    if (yy<height-1){
                        yy=yy+1;
                        pixels = raster.getPixel(xx, yy, (int[]) null);
                        color2B=pixels[2];
                    }else{
                        break;
                    }  
                }
             
                if (contadorIgualesB>1){
                    String salida=contadorIgualesB +"x"+color1B;
                    codifB.add(salida);
                }
                else{
                    String salida = ""+ color1B;
                    codifB.add(salida);
                }
                if (color1B!=color2B){
                   yy=yy-1;
                }  

               }
        }
        //las tres filas de codificacion se agregan a la lista de listas y se retorna
        codificacion.add(codifR);
        codificacion.add(codifG);
        codificacion.add(codifB);
        return codificacion;
    }
    //metodo para decodificar, recibe la matriz de codificacion y el tamaño de la imagen original
    private static BufferedImage Decodificar( LinkedList<LinkedList<String>> codificacion,int width ,int height ) {
        //se crea una imagen en blanco del mismo tamaño de la original
        BufferedImage bufimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int CodTamanoR = codificacion.get(0).size();
        int CodTamanoG = codificacion.get(1).size();
        int CodTamanoB = codificacion.get(2).size();
        WritableRaster raster = bufimage.getRaster();
        int ccR=0;
        //se recorre la matriz red de la imagen
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                //obtiene el arreglo que representa los colores del pixel
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                String posCodR=codificacion.get(0).get(ccR);
                //identifica si la posicion se codifico (tiene varios pixeles repetidos) o no
                if(posCodR.contains("x")){
                    //si se codifico se separa el numero de veces que se repite el color y el valor de dicho color
                    String[] arregloTemp=posCodR.split("x");
                    int repeticiones=Integer.parseInt(arregloTemp[0]);
                    int valor=Integer.parseInt(arregloTemp[1]);
                    //asigna a todos los pixeles que se repiten el valor correspondiente
                    for (int y=yy;y<yy+repeticiones;y++){
                        pixels = raster.getPixel(xx, y, (int[]) null);
                        pixels[0]=valor;
                        raster.setPixel(xx, y, pixels);
                    }
                    ccR=ccR+1;
                    yy=yy+repeticiones-1;
                } else{
                    //si no habia pixeles repetidos los guarda sin cambios
                    pixels[0]=Integer.parseInt(posCodR);
                    raster.setPixel(xx, yy, pixels);
                    ccR=ccR+1;
                }

            }
        }
        //igualmente se hace para las matrices green y blue
        int ccG=0;
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                String posCodG=codificacion.get(1).get(ccG);
                if(posCodG.contains("x")){
                    String[] arregloTemp=posCodG.split("x");
                    int repeticiones=Integer.parseInt(arregloTemp[0]);
                    int valor=Integer.parseInt(arregloTemp[1]);
                    for (int y=yy;y<yy+repeticiones;y++){
                        pixels = raster.getPixel(xx, y, (int[]) null);
                        pixels[1]=valor;
                        raster.setPixel(xx, y, pixels);
                    }
                    ccG=ccG+1;
                    yy=yy+repeticiones-1;
                    
                } else{
                    
                    pixels[1]=Integer.parseInt(posCodG);
                    raster.setPixel(xx, yy, pixels);
                    ccG=ccG+1;
                }

            }
        }
        int ccB=0;
        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                String posCodB=codificacion.get(2).get(ccB);
                if(posCodB.contains("x")){
                    String[] arregloTemp=posCodB.split("x");
                    int repeticiones=Integer.parseInt(arregloTemp[0]);
                    int valor=Integer.parseInt(arregloTemp[1]);
                    for (int y=yy;y<yy+repeticiones;y++){
                        pixels = raster.getPixel(xx, y, (int[]) null);
                        pixels[2]=valor;
                        raster.setPixel(xx, y, pixels);
                    }
                    ccB=ccB+1;
                    yy=yy+repeticiones-1;
                } else{
                    
                    pixels[2]=Integer.parseInt(posCodB);
                    raster.setPixel(xx, yy, pixels);
                    ccB=ccB+1;
                }

            }
        }
        //retorna la imagen decodificada
        return bufimage;
    }

}