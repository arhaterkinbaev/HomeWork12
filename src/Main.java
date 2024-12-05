class IdleState implements TicketMachineState {
    private final TicketMachine machine;

    public IdleState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Билет выбран. Ожидание внесения денег.");
        machine.setState(machine.getWaitingForMoneyState());
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Сначала выберите билет.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Выдача билета невозможна. Билет не выбран.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Отменить нечего. Автомат ожидает действия.");
    }
}

class WaitingForMoneyState implements TicketMachineState {
    private final TicketMachine machine;

    public WaitingForMoneyState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Билет уже выбран. Внесите деньги.");
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Внесено " + amount + " денег.");
        machine.addMoney(amount);

        if (machine.getCurrentBalance() >= machine.getTicketPrice()) {
            machine.setState(machine.getMoneyReceivedState());
        }
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Выдача билета невозможна. Сначала внесите деньги.");
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Транзакция отменена.");
        machine.resetBalance();
        machine.setState(machine.getIdleState());
    }
}

class MoneyReceivedState implements TicketMachineState {
    private final TicketMachine machine;

    public MoneyReceivedState(TicketMachine machine) {
        this.machine = machine;
    }

    @Override
    public void selectTicket() {
        System.out.println("Билет уже выбран. Ожидание выдачи.");
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Деньги уже внесены. Ожидание выдачи билета.");
    }

    @Override
    public void dispenseTicket() {
        System.out.println("Билет выдан. Спасибо за покупку!");
        machine.resetBalance();
        machine.setState(machine.getIdleState());
    }

    @Override
    public void cancelTransaction() {
        System.out.println("Транзакция отменена.");
        machine.resetBalance();
        machine.setState(machine.getIdleState());
    }
}

class TicketMachine {
    private final TicketMachineState idleState;
    private final TicketMachineState waitingForMoneyState;
    private final TicketMachineState moneyReceivedState;

    private TicketMachineState currentState;
    private double currentBalance = 0.0;
    private final double ticketPrice = 50.0;

    public TicketMachine() {
        idleState = new IdleState(this);
        waitingForMoneyState = new WaitingForMoneyState(this);
        moneyReceivedState = new MoneyReceivedState(this);
        currentState = idleState;
    }

    public void setState(TicketMachineState state) {
        currentState = state;
    }

    public void selectTicket() {
        currentState.selectTicket();
    }

    public void insertMoney(double amount) {
        currentState.insertMoney(amount);
    }

    public void dispenseTicket() {
        currentState.dispenseTicket();
    }

    public void cancelTransaction() {
        currentState.cancelTransaction();
    }

    public TicketMachineState getIdleState() {
        return idleState;
    }

    public TicketMachineState getWaitingForMoneyState() {
        return waitingForMoneyState;
    }

    public TicketMachineState getMoneyReceivedState() {
        return moneyReceivedState;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void addMoney(double amount) {
        currentBalance += amount;
    }

    public void resetBalance() {
        currentBalance = 0.0;
    }
}

public class Main {
    public static void main(String[] args) {
        TicketMachine machine = new TicketMachine();

        machine.selectTicket();
        machine.insertMoney(30);
        machine.insertMoney(20);
        machine.dispenseTicket();

        machine.selectTicket();
        machine.insertMoney(10);
        machine.cancelTransaction();
    }
}
