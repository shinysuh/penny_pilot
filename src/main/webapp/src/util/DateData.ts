import { TransactionPeriodType } from '../type/TransactionType.ts'

export const getCurrentDateByPeriodType = (periodType?: string): string => {
  const date = new Date()

  const yyyy = date.getFullYear()
  if (periodType === TransactionPeriodType.YEAR) {
    return yyyy.toString()
  }

  const mm = String(date.getMonth() + 1).padStart(2, '0') // 월은 0부터 시작하므로 +1
  if (!periodType || periodType === TransactionPeriodType.MONTH) {
    return `${yyyy}-${mm}`
  }

  const dd = String(date.getDate()).padStart(2, '0')
  if (periodType === TransactionPeriodType.DAY) {
    return `${yyyy}-${mm}-${dd}`
  }

  return ''
}

/**
 * 지정 월의 날짜 수
 * @param dateStr
 */
export const getDaysInMonth = (dateStr: string) => {
  const year = parseInt(dateStr.slice(0, 4))
  const month = parseInt(dateStr.slice(5, 7))
  return new Date(year, month, 0).getDate()
}

export const getDaysAndMonth = (dateStr: string) => {
  const year = parseInt(dateStr.slice(0, 4))
  const month = parseInt(dateStr.slice(5, 7))
  const date = new Date(year, month, 0)

  const monthName = date.toLocaleDateString('en-US', {
    month: 'short',
  })
  const daysInMonth = date.getDate()
  const days = []
  let i = 1
  while (days.length < daysInMonth) {
    days.push(`${monthName} ${i}`)
    i += 1
  }

  return days
}

export const getDateFormatByPeriodType = (periodType: string): string => {
  switch (periodType) {
    case TransactionPeriodType.YEAR:
      return 'YYYY'
    case TransactionPeriodType.MONTH:
      return 'YYYY-MM'
    case TransactionPeriodType.DAY:
      return 'YYYY-MM-DD'
    default:
      return ''
  }
}
