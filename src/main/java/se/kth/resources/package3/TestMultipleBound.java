package se.kth.resources.package3;

class Bound2 {

    private B2 objRef;

    public Bound2(B2 obj){
        this.objRef = obj;
    }

    public void doRunTest(){
        this.objRef.displayClass();
    }
}

interface B2
{
    public void displayClass();
}

interface B3
{
    public void displayClass3();
}

class A2 implements B2, B3
{
    public void displayClass()
    {
        System.out.println("Inside super class A");
    }

    @Override
    public void displayClass3() {

    }
}

public class TestMultipleBound
{
    public static void main(String a[])
    {
        //Creating object of sub class A and
        //passing it to Bound as a type parameter.
        Bound2 bea = new Bound2(new B2() {
            @Override
            public void displayClass() {

            }
        });
        bea.doRunTest();

    }
}
