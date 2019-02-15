/* В моих проектах не нашлось места фабрикам,
попробовал реализовать абстрактную фабрику с учетом UML - диаграммы ДЗ урока 2
 */

enum ExpenseItems{                     //статьи расходов
    TAXES, MULCT, PURCHASE             //перечисление просто для удобства
}

interface Monetary{                      //денежный
    void getAccount(String account, float money, float rate);
    float getBalance();
    float getBalance(long date);
    void update(float money, long date);
}

interface Deductible{                     //вычитаемый
    void write_off(Monetary cashAccount); //списать(Денежный счет)
}

interface Expendable{                    // расходуемый - интерфейс абстрактной фабрики
    Deductible getAnExpense(ExpenseItems items);
}

class CashExpenses implements Deductible{//денежные расходы
    @Override
    public void write_off(Monetary cashAccount) {

    }
}

class Taxes extends CashExpenses{       //налоги
    @Override
    public void write_off(Monetary cashAccount) {

    }
}

class Mulct extends CashExpenses{       //штрафы
    @Override
    public void write_off(Monetary cashAccount) {

    }
}

class Purchase extends CashExpenses{    //покупки
    @Override
    public void write_off(Monetary cashAccount) {

    }
}

class Expenses implements Expendable{  // фабрика - расходы
    @Override
    public Deductible getAnExpense(ExpenseItems items) {
        switch (items){
            case TAXES:
                return new Taxes();
            case MULCT:
                return new Mulct();
            case PURCHASE:
                return new Purchase();
        }
        return null;
    }
}
