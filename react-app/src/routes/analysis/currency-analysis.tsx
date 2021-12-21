import { Box, Typography } from "@mui/material";
import { DataGrid, GridColDef, GridToolbar } from "@mui/x-data-grid";
import { useQuery } from "react-query";
import {
  CurrencyNetWorth,
  NetWorthCurrencyResponse,
} from "../../types/axios-responses/NetWorthCurrencyResponse";
import { Response } from "../../types/axios-responses/Response";
import { fetchNetWorthCurrencies } from "../../axios/queries";
import { useAppSelector } from "../../redux/hooks/redux";
import { selectAxiosConfig } from "../../redux/slices/axiosSlice";

function CurrencyAnalysis() {
  const axiosConfig = useAppSelector(selectAxiosConfig);

  const { isLoading, error, data, isSuccess } = useQuery<
    Promise<NetWorthCurrencyResponse>,
    Response
  >("netWorthCurrencies", () => fetchNetWorthCurrencies(axiosConfig));

  if (isLoading) {
    return <span>Loading...</span>;
  }

  if (isSuccess) {
    let columns: GridColDef[] = [];
    let rows = [];

    /* @ts-ignore */
    if (data.entity.length > 0) {
      /* @ts-ignore */
      columns = [
        {
          field: "currency",
          headerName: "currency",
          flex: 1,
        },
        {
          field: "netWorth",
          headerName: "Net Worth",
          flex: 1,
        },
      ];
      /* @ts-ignore */
      rows = data.entity.map((x: CurrencyNetWorth, index) => ({
        ...x,
        id: index,
      }));
    }

    return (
      <div style={{ display: "flex", height: "100%" }}>
        <div style={{ flexGrow: 1 }}>
          <Typography sx={{ marginY: 4 }} variant="h2" gutterBottom={true}>
            Holdings Aggregate
          </Typography>
          <Typography sx={{ marginY: 4 }} gutterBottom={true}>
            This serves as an overall accounting of all the crypto you've traded
            / transferred on Gemini.
            <br />
            Positive values represents an excess amount of crypto you're holding
            in gemini.
            <br />
            Negative values represents used costs, ie. purchases, fees, and
            withdrawals.
          </Typography>
          <Box
            sx={{
              display: "flex",
              flexDirection: "row",
              margin: 5,
              justifyContent: "flex-end",
              alignItems: "flex-end",
              fontWeight: "bold",
            }}
          />

          {rows.length === 0 &&
            "Looks like you don't have any Trades, please import or start your first Trade in gemini!."}

          <DataGrid
            autoHeight
            rows={rows}
            columns={columns}
            components={{
              Toolbar: GridToolbar,
            }}
          />
        </div>
      </div>
    );
  }

  return <span>Error: {error?.message}</span>;
}

export { CurrencyAnalysis };
