 
import java.util.Scanner;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class cdma {
 static int num;
   
   public List bipolar(String key){
       List<Integer> listKey =new ArrayList<Integer>();
        for(int i = 0; i<key.length();i++){
        listKey.add(Character.getNumericValue(key.charAt(i)));
    }            
        listKey.replaceAll(new Bipolar());
        return listKey;
   }

   public int sum(List<Integer> key1,List<Integer> key2){
       List<Integer> temp =new ArrayList<Integer>();
       for(int i=0;i<key1.size();i++){
           temp.add(key1.get(i)*key2.get(i));
       }
       int sum = 0;
        for(int d : temp)
            sum += d;
       return sum;
   }

   public boolean orthogonal(List<List<Integer>> keys){
      Map m1 = new HashMap(); 
      
      for(int i=0;i< num;i++){
            cdma x= new cdma();
            m1.put(String.valueOf(i+1),true);
            for( int j=i+1;j<num;j++){
                if(sum(keys.get(i),keys.get(j)) != 0)
                    m1.replace(String.valueOf(i+1),false);
            }
        }
    System.out.println(m1.values());
    if(m1.values().contains(false))
       return false;
    else
        return true;
   }
//    public static List<List<Integer>> clone(List<List<Integer>> original) {
//         List<List<Integer>> copy = new ArrayList<List<Integer>>();
//         copy.addAll(original);
//         return copy;
// }
    public static void main(String[] args) {
        System.out.println("Enter no. of users");
        Scanner myObj = new Scanner(System.in);
        num = myObj.nextInt();
            List<List<Integer>> keys = new ArrayList<List<Integer>>();
            List<List<Integer>> encodedKeys=new ArrayList<List<Integer>>();
            Map data = new HashMap(); 

            for(int i=0;i<num;i++){
                System.out.println("Enter Key");
                Scanner newObj = new Scanner(System.in);
                String key= newObj.nextLine();
                System.out.println("Enter Data:");
                data.put(String.valueOf(i+1),newObj.nextInt());
                cdma m= new cdma();
                keys.add(m.bipolar(key));
                encodedKeys.add(m.bipolar(key));
            }
        System.out.println("Keys: "+keys);// bipolar representation
        System.out.println("Data: "+data.values());
        //orthogonal
        cdma m= new cdma();
        // while(!m.orthogonal(keys)){

        // }
        
        
        
        List<Integer> dataList = new ArrayList<>(data.values());
        for(int i=0;i<keys.size();i++){
            encodedKeys.get(i).replaceAll(new MyOperator(dataList.get(i)));
            if(dataList.get(i) == 0)
                encodedKeys.get(i).replaceAll(new Bipolar());
        }
        System.out.println("Encoded Keys: "+encodedKeys);
        List<Integer> transmissionMessage =new ArrayList<Integer>();
       
       for(int i=0;i<encodedKeys.get(0).size();i++){
           int sum=0;
           for(List<Integer> j : encodedKeys)
                sum=sum+j.get(i);
            transmissionMessage.add(sum);
    }
       System.out.println("Message: "+transmissionMessage);
        
       //Receiver Side
       System.out.println("*******************************Receiver************************************");
       List<Integer> received =new ArrayList<Integer>();
       for(List<Integer> j : keys){
           int sum=0;
       for(int i=0;i<keys.get(0).size();i++)
            sum=sum+transmissionMessage.get(i)*j.get(i);
            
       received.add(sum);
       }
       System.out.println("Data Received: "+received);
}
}
class MyOperator implements UnaryOperator<Integer> 
{
    private static int factor;
    MyOperator(int data){
        factor=data;
    }
    @Override
    public Integer apply(Integer t) {
        return factor*t;
    }
}
class Bipolar implements UnaryOperator<Integer> 
{
    @Override
    public Integer apply(Integer t) {
        if(t== 0)
            return -1;
        else
            return 1;
    }
}
