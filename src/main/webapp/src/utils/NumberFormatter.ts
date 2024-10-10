import { LocaleType } from '../types/LocaleType.ts'
import { CurrencyType } from '../types/CurrencyType.ts'

/**
 * 1000000 => 100만원
 */
export const compactNumberFormatter = new Intl.NumberFormat(LocaleType.KO, {
  notation: 'compact', // 축약 표기 사용
} as Intl.NumberFormatOptions)

/**
 * 1000000 => 1,000,000
 */
export const groupedNumberFormatter = (currency: string) => {
  // 소수점 둘째 자리까지 표기 여부
  const noFractionDigits = currency === CurrencyType.KRW

  return noFractionDigits
    ? new Intl.NumberFormat(LocaleType.EN, {
        style: 'decimal',
        useGrouping: true,
      })
    : new Intl.NumberFormat(LocaleType.EN, {
        style: 'decimal',
        useGrouping: true,
        minimumFractionDigits: 2,
        maximumFractionDigits: 2,
      })
}
