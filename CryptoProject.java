/*
Title: Cryptography Project
Authors: Collin Woodruff, Colby Wellens
 */
package cryptoproject;
import java.util.*;
public class CryptoProject {
    public static void main(String[] args) {
        int stop = 1;
        Scanner input = new Scanner(System.in);
        //test();
        int n = 18923, e = 1261;    //Public Key Values
        int p, q, d, c, ct;         //P, Q, D, C, Cipher Text Values
        char fL, sL, tL;            //First, Second, Third Letter Variables 
        p = factorP(n);             //Factors N to find P
        q = factorQ(p,n);           //Factors N to find Q
        d = multiplicativeInverse(e, p, q); 
        System.out.println("Enter the block number to decrypt: ");
        System.out.println("Enter 0 if you want to stop");
        while(stop > 0){
            c = input.nextInt();
            if(c > 0){
              ct = modExponentation(c,d,n);
              fL = firstLetter(ct);
              sL = secondLetter(ct);
              tL = thirdLetter(ct);
              System.out.println("The string of the three letters is: " + fL + sL + tL);
            }
            else
                stop = c;
        }
    }
    
    public static int factorP(int n){
        int p = 0;
        boolean found = false;
        double tempn;
        tempn = Math.sqrt((double)n);
        p = (int)tempn;             //Typecast Square root to its integer value
        if(p % 2 == 0){
            p = p - 1;              //If P is even make it odd
        }
        while(!found){
            if(n % p == 0){
                if(isPrime(p) == true){     //Checks if P is prime 
                    found = true;                    
                }
            }
            else{
                p = p - 2;          //Cycle through to find P value
            }
        }
        return p;                   //returns P
    }
    
    public static int factorQ(int p, int n){
        int q = 0;
        q = n / p;                 //since Q = N / P this equation works
        return q;
    }
    
    public static int multiplicativeInverse(int e, int p, int q){
        int m = (p - 1) * (q - 1);
        int m0 = m, t, r;
        int x0 = 0, x1 = 1;
 
    if (m == 1)
      m = 0;                      //Base case if m = 1 then m = 0
 
    while (e > 1)
    {
        r = e / m;                //r is the quotient
        
        t = m;                    /*t is first value to be moved to the next 
                                  equation*/
        
        m = e % m;                /*m is the remainder and second value to be 
                                  moved to the next equation*/
        
        e = t;                    /*move t to the first value for the next 
                                  equation*/ 
        
        t = x0;                   //stores x0 value 
        
        x0 = x1 - r * x0;         /*equation to continuously compute the 
                                  Euclidean algorithm*/
        
        x1 = t;                   //stores original x0 value in x1
    }
 
    if (x1 < 0){
       x1 = x1 + m0;              //Make x1 positive
    }
    return x1;
    }
    
    public static boolean isPrime(int p){   /*function to check if a 
                                            number is prime*/
        for(int i = 2; i < p; i++){
            if(p % i == 0){
                return false;
            }
        }
        return true;                
    }
    
    public static int modExponentation(int c, int d, int n){
        int ct = 1;
        while (d > 0)
        {
            if (d % 2 == 1){
                ct = (ct * c) % n;     /*once the exponent reaches one computes 
                                       ciphertext*/
            }
            d = d / 2;                 /*divides the exponent by 2 during 
                                       repeated squaring*/
            
            c = (c * c) % n;           /*equation to perform the repeated 
                                       squaring*/
        }
        return ct;
    }
    
    public static char firstLetter(int ct){
        char [] alphabet = new char[26];
        int fL0;
        char fL1 = 'a';
        fL0 = ct / (int)Math.pow(26,2);    /*finds the location in the alphabet 
                                           of the first letter*/ 
        
        for(int i = 0; i < 26; i++){       //creates the alphabet in the array 
            alphabet[i] = fL1;              
            fL1++;
            }
        fL1 = alphabet[fL0];       /*find the letter by using the alphabet 
                                   array and the location of the first letter*/ 
        return fL1;
    }
    
    public static char secondLetter(int ct){
        char [] alphabet = new char[26];
        int sL0;
        char sL1 = 'a';
        int fL = ct / (int)Math.pow(26,2);   /*finds the location in 
                                             the alphabet of the first letter*/
        
        sL0 = (ct - ((int)Math.pow(26,2)) * fL) / 26; 
        /*finds the location in the alphabet of the second letter using the 
        location of the first*/
        
        for(int i = 0; i < 26; i++){       //creates the alphabet in the array
            alphabet[i] = sL1;
            sL1++;
            }
        sL1 = alphabet[sL0];    /*find the letter by using the alphabet 
                                array and the location of the second letter*/ 
        return sL1;
    }
    
    public static char thirdLetter(int ct){
        char [] alphabet = new char[26];
        int tL0;
        char tL1 = 'a';
        int fL = ct / (int)Math.pow(26,2);  /*finds the location in 
                                             the alphabet of the first letter*/
        
        int sL = (ct - ((int)Math.pow(26,2)) * fL) / 26; 
        /*finds the location in the alphabet of the second letter using the 
        location of the first*/
        
        tL0 = (ct - ((int)Math.pow(26,2)) * fL) - (26 * sL); 
        /*finds the location in the alphabet of the third letter using the 
        location of the first and the second*/
        
        for(int i = 0; i < 26; i++){       //creates the alphabet in the array
            alphabet[i] = tL1;          
            tL1++;
            }
        tL1 = alphabet[tL0];    /*find the letter by using the alphabet 
                                array and the location of the third letter*/ 
        return tL1;
    }  
}
