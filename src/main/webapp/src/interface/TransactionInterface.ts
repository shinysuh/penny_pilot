export interface ITransaction {
  id: number;
  accountId: number;
  ctgId: number;
  amount: number;
  transactionType: string;
  transactionDate: string;
  description: string;
  createdAt: string;
  updatedAt: string;
}

export interface IPeriodParam {
  userId: number;
  transactionPeriod: string;
  periodType?: string; // default: MONTH
}

export interface ITransactionResult extends ITransaction {
  bankName: string;
  accountName: string;
  accountType: string;
  ctgNm: string;
}

export interface IMonthlyTransaction {
  transactionMonth: string; //yyyy-mm
  totalIncome: number;
  totalExpense: number;
  transactionByDays: IDailyTransaction[];
}

export interface IDailyTransaction {
  day: string;
  totalIncome: number;
  totalExpense: number;
  transactions: ITransactionResult[];
}

export interface ITotalByPeriod {
  transactionPeriod: string;
  transactions: ITotalByType[];
}

export interface ITotalByType {
  transactionType: string;
  totalAmount: number;
  ctgTotals: ITotalByCtg[];
}

export interface ITotalByCtg {
  transactionType: string;
  ctgId: number;
  ctgNm: string;
  totalAmount: number;
}
