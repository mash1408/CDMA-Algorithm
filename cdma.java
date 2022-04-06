 
import java.util.Scanner;
import java.util.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import javax.swing.*;
import java.awt.event.*; 
import java.awt.Frame;
import java.awt.Button;
import javax.swing.JOptionPane; 

public class cdma {
 static int num;
 static List<List<Integer>> keys;
 static List<List<Integer>> encodedKeys;
 static Map data;
 static Frame f;
   
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
      for(int i=0;i< num;i++){
            cdma x= new cdma();
            for( int j=i+1;j<num;j++){
                if(x.sum(keys.get(i),keys.get(j)) != 0)
                    return false;
            }
        }
        return true;
   }

   public static void displayData(){
       f.dispose();
    Frame fx=new Frame("ActionListener Example");
    JLabel l1,l2,l3,l4,l5,l6,l7;  
    l1=new JLabel("_____________________Sender_______________________");  
    l1.setBounds(50,50, 300,50);  
    l2=new JLabel("Keys:");  
    l2.setBounds(50,75, 100,30);
    l3=new JLabel("Data:");  
    l3.setBounds(50,100, 100,30); 
    l4=new JLabel("EncodedKeys:");  
    l4.setBounds(50,125, 100,30);
    l5=new JLabel("Message:");  
    l5.setBounds(50,150, 100,30);
    l6=new JLabel("___________________Receiver_______________________");  
    l6.setBounds(50,200, 300,50);
    l7=new JLabel("Data:");  
    l7.setBounds(50,225, 100,30);   
    fx.add(l1); fx.add(l2);fx.add(l3);fx.add(l4);fx.add(l5);fx.add(l6);fx.add(l7);
     fx.setSize(400,400);
    fx.setLayout(null);  
    fx.setVisible(true);  
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
   public static void getValues(int k){
        if(k==0){
            cdma m= new cdma();
            if(m.orthogonal(keys))
                cdma.displayData();
            else{    
                JOptionPane.showMessageDialog(f, "Codes are not orthogonal","Type again" ,JOptionPane.ERROR_MESSAGE);
                getValues(num);
            }
            return;
        }
        JTextField t1,t2; 
        t1=new JTextField("key"+String.valueOf(cdma.num-k +1));
        t1.setBounds(50,50,150,20);
        t2=new JTextField("data");
        t2.setBounds(50,75,150,20);
        Button bNext=new Button("Next");
        bNext.setBounds(50,100,150,20);
        bNext.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){  
            data.put(String.valueOf(cdma.num-k +1),Integer.parseInt(t2.getText()));
            cdma m= new cdma();
            keys.add(m.bipolar(t1.getText()));
            encodedKeys.add(m.bipolar(t1.getText()));
            cdma.f.removeAll();
            cdma.getValues(k-1);
        }  
        });
        f.add(bNext);
        f.add(t1);f.add(t2); 
}
    public static void main(String[] args) {
         f=new Frame("ActionListener Example");
        JTextField t1; 
        t1=new JTextField("users");
        t1.setBounds(50,50,150,20);
        Button bNum=new Button("Submit");
        bNum.setBounds(50,100, 150,20);
        bNum.addActionListener(new ActionListener(){  
        public void actionPerformed(ActionEvent e){  
            cdma.num=Integer.parseInt(t1.getText());
            cdma.f.removeAll();
            cdma.getValues(num);
        }  
        });   
        f.add(bNum);f.add(t1);  
        f.setSize(400,400);
        f.setLayout(null);  
        f.setVisible(true);  
        keys = new ArrayList<List<Integer>>();
        encodedKeys=new ArrayList<List<Integer>>();
        data = new HashMap();  
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
