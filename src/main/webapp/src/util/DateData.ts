import { TransactionPeriodType } from '../type/TransactionPeriodType.ts';

export const getCurrentDateByPeriodType = (periodType?: string): string => {
  const date = new Date();

  const yyyy = date.getFullYear();
  if (periodType === TransactionPeriodType.YEAR) {
    return yyyy.toString();
  }

  const mm = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
  if (!periodType || periodType === TransactionPeriodType.MONTH) {
    return `${yyyy}-${mm}`;
  }

  const dd = String(date.getDate()).padStart(2, '0');
  if (periodType === TransactionPeriodType.DAY) {
    return `${yyyy}-${mm}-${dd}`;
  }

  return '';
};

export const getDateFormatByPeriodType = (periodType: string): string => {
  switch (periodType) {
    case TransactionPeriodType.YEAR:
      return 'YYYY';
    case TransactionPeriodType.MONTH:
      return 'YYYY-MM';
    case TransactionPeriodType.DAY:
      return 'YYYY-MM-DD';
    default:
      return '';
  }
};
