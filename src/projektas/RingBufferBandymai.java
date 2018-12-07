
package projektas;

import java.io.PrintStream;

public class RingBufferBandymai
{
    private static final PrintStream p = System.out;
    
    public static void demoOperations()
    {
        RingBufferFixedArray<String> r1 = new RingBufferFixedArray<>(8);
        
        RingBufferFixedArray<String> r2 = new RingBufferFixedArray<>(8);       
        
        p.println("Pridedami elementai naudojant insert.");
        r1.insert("Labas"); r2.insert("labas");
        r1.insert("rytas"); r2.insert("rytas");
        r1.insert("pasauli"); r2.insert("pasauli");
        r1.insert("jau"); r2.insert("jau");
        r1.insert("graži"); r2.insert("graži");
        r1.insert("diena"); r2.insert("diena");
        r1.insert("išaušo"); r2.insert("išaušo");
        r1.insert(":)"); r2.insert(":)");
        r1.println();
        
        p.println();
        p.println("Pašaliname elementą- " + r1.pop());
        p.println("Pašaliname elementą- " + r1.pop());
        p.println();
        r1.println();
        
        p.println("Surikiuotas sąrašas, pagal abėcelę");
        r2.sort();
        r2.println();
        p.println();  
              
        p.println();
        p.println("Išvalome sąrašą.");        
        r1.clear();
        r1.println();
        p.println();
               
        r1.push("Bandymas");
        r1.push("Vienas");
        r1.push("Du");
        p.println("Pridedamas elementas į pradžią");
        p.println(r1.get(0));
        p.println();
        
    }
    
    public static void Demo2()
    {
        RingBufferFixedArray<String> r1 = new RingBufferFixedArray<>(5);
                 
        r1.insert("Labas");
        r1.insert("rytas");
        r1.insert("pasauli");
        r1.insert("jau");
        r1.insert("graži");
        r1.insert("diena");
        r1.insert("išaušo");
        r1.insert(":)");
        r1.println();
        
    }
    
    public static void greitaveikosTyrimas()
    {
        int ringSize = 200;
        int maxSize = 1000000;
        RingBufferFixedArray<Integer> r1 =
                new RingBufferFixedArray<>(ringSize);
        RingBufferFixedArray<Integer> r2 =
                new RingBufferFixedArray<>(maxSize);
        long t0 = System.nanoTime();
        for (int k = 0; k < 10; k++)
        {
            for (int i = 0; i < maxSize; i++)
            {
                r1.insert(i);
            }
            for (int i = 0; i < maxSize; i++)
            {
                r2.set(i, i);
            }
            System.gc(); System.gc(); System.gc();
        }
        long t1 = System.nanoTime();
        System.out.println("dt= "+(t1-t0)*1e-9);
    }
    
    public static void main(String[] args) {
        System.setErr(p);
        demoOperations();
        Demo2();
        greitaveikosTyrimas();
    }
}
