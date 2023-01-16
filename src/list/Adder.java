package list;

public class Adder {
    public  static void Adder(Input input,Output output) {
        Integer sum = atomAdder(input.getInput1(), input.getInput2(), input.getCarry());
        output.setCarry(input.carry);
        output.setOutput(sum);
    }
    private static Integer atomAdder(Integer input1, Integer input2, Carry carry) {
        if (input1 == null) input1 = 0;
        if (input2 == null) input2 = 0;
        int real = input1 + input2 + carry.getCarry();
        int current = real % 10;
        carry.setCarry(real / 10);// 更新
        return current;
    }

    /**
     * input
     */
    class Input{
         Integer input1 = 0;
         Integer input2 = 0;
         Carry carry = new Carry(0);


        public  Integer getInput1() {
            return input1;
        }

        public  Integer getInput2() {
            return input2;
        }

        public  Carry getCarry() {
            return carry;
        }

    }

    /**
     * carry
     */
     class Carry {
        Integer carry = 0;

        public Carry(int i) {
            carry = i;
        }
        public Integer getCarry() {
            return carry;
        }
        public void setCarry(Integer carry) {
            this.carry = carry;
        }
    }

    /**
     * output
     */
    class Output{
        Carry carry;
        Integer output;

        public Output(Carry carry, Integer output) {
            this.carry = carry;
            this.output = output;
        }

        public Carry getCarry() {
            return carry;
        }
        public void setCarry(Carry carry) {
            this.carry = carry;
        }

         public void setOutput(Integer output) {
             this.output = output;
         }
     }
}
