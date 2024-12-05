public interface TicketMachineState {
    void selectTicket();
    void insertMoney(double amount);
    void dispenseTicket();
    void cancelTransaction();
}
