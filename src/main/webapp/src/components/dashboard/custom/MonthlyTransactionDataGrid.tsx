import * as React from 'react'
import { DataGrid, GridColDef } from '@mui/x-data-grid'
import {
  IMonthlyTransaction,
  ITransactionResult,
} from '../../../interfaces/TransactionInterface.ts'
import Chip from '@mui/material/Chip'
import { formatTimeStr } from '../../../utils/DateTimeData.ts'
import { TransactionType } from '../../../types/TransactionType.ts'
import { groupedNumberFormatter } from '../../../utils/NumberFormatter.ts'
import { IUser } from '../../../interfaces/UserInterface.ts'

type MonthlyTransactionDataGridProperties = {
  user: IUser
  monthlyTRA: IMonthlyTransaction
}

const MonthlyTransactionDataGrid = (
  props: MonthlyTransactionDataGridProperties,
) => {
  const [transactions, setTransactions] = React.useState<ITransactionResult[]>(
    [],
  )

  React.useEffect(() => {
    setTransactions(
      props.monthlyTRA.transactionByDays.flatMap((daily) => daily.transactions),
    )
  }, [props.monthlyTRA])

  const columns: GridColDef[] = [
    {
      field: 'ctgNm',
      headerName: '분류',
      flex: 1,
      minWidth: 100,
      hideSortIcons: true,
    },
    {
      field: 'description',
      headerName: '상세',
      headerAlign: 'left',
      align: 'left',
      flex: 3,
      minWidth: 160,
      hideSortIcons: true,
      // renderCell: (params) => renderStatus(params.value as any),   // TODO => 금액 색깔 만들 때 쓰기
    },
    {
      field: 'transactionDate',
      headerName: '시각',
      headerAlign: 'center',
      align: 'center',
      flex: 1,
      minWidth: 100,
      hideSortIcons: true,
      valueFormatter: (value) => formatTimeStr(value),
    },
    {
      field: 'bankName',
      headerName: '은행',
      headerAlign: 'center',
      align: 'center',
      flex: 1,
      minWidth: 100,
      hideSortIcons: true,
    },
    {
      field: 'accountName',
      headerName: '자산',
      headerAlign: 'center',
      align: 'center',
      flex: 1,
      minWidth: 100,
      hideSortIcons: true,
    },
    {
      field: 'amount',
      headerName: '금액',
      headerAlign: 'right',
      align: 'right',
      flex: 1,
      minWidth: 120,
      hideSortIcons: true,
      renderCell: (params) =>
        renderAmountCell(params.value, params.row.transactionType),
    },
  ]

  const renderAmountCell = (amount: number, transactionType: string) => {
    let color: string =
      transactionType === TransactionType.INCOME
        ? 'blue'
        : transactionType === TransactionType.EXPENSE
          ? 'red'
          : 'black'

    return (
      <div style={{ color: color }}>
        {groupedNumberFormatter(props.user.currency).format(amount)}원
      </div>
    )
  }

  function renderStatus(status: 'Online' | 'Offline') {
    const colors: { [index: string]: 'success' | 'default' } = {
      Online: 'success',
      Offline: 'default',
    }

    return <Chip label={status} color={colors[status]} size="small" />
  }

  return (
    <DataGrid
      autoHeight
      checkboxSelection
      rows={transactions}
      columns={columns}
      getRowClassName={(params) =>
        params.indexRelativeToCurrentPage % 2 === 0 ? 'even' : 'odd'
      }
      initialState={{
        pagination: { paginationModel: { pageSize: 20 } },
      }}
      pageSizeOptions={[10, 20, 50]}
      disableColumnResize
      density="compact"
      slotProps={{
        filterPanel: {
          filterFormProps: {
            logicOperatorInputProps: {
              variant: 'outlined',
              size: 'small',
            },
            columnInputProps: {
              variant: 'outlined',
              size: 'small',
              sx: { mt: 'auto' },
            },
            operatorInputProps: {
              variant: 'outlined',
              size: 'small',
              sx: { mt: 'auto' },
            },
            valueInputProps: {
              InputComponentProps: {
                variant: 'outlined',
                size: 'small',
              },
            },
          },
        },
      }}
    />
  )
}

export default MonthlyTransactionDataGrid
