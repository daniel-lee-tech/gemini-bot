import {
  Accordion,
  AccordionDetails,
  AccordionSummary,
  Box,
  Typography,
} from "@mui/material";
import { ReactElement } from "react";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import { ApiKeysForm } from "../components/ApiKeysForm/api-keys-form";
import { useQuery } from "react-query";
import { ApiKeysResponse } from "../types/axios-responses/ApiKeysResponse";
import { Response } from "../types/axios-responses/Response";
import { fetchKeys } from "../axios/queries";
import { ApiKey } from "../schemas/ApiKey";
import { useAppSelector } from "../redux/hooks/redux";
import { selectAxiosConfig } from "../redux/slices/axiosSlice";

function ApiKeys(): ReactElement {
  const axiosConfig = useAppSelector(selectAxiosConfig);

  const { isLoading, error, data, isSuccess } = useQuery<
    Promise<ApiKeysResponse>,
    Response
  >("apiKeys", () => fetchKeys(axiosConfig));

  if (isLoading) {
    return <span>Loading...</span>;
  }

  let keyTypes = ["AUDITOR", "ADMINISTRATOR", "MANAGER", "TRADER"];

  if (isSuccess) {
    return (
      <Box>
        <Typography variant="h2" align="center" gutterBottom={true}>
          Your API Keys
        </Typography>
        {/* @ts-ignore */}
        {data.entity.map((apiKey: ApiKey) => (
          <Accordion key={apiKey.type}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls={`panel-${apiKey.type}-content`}
              id={`panel-${apiKey.type}-header`}
            >
              <Typography>{apiKey.type}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <ApiKeysForm
                type={apiKey.type}
                publicKey={apiKey.publicKey}
                secretKey={apiKey.secretKey}
              />
            </AccordionDetails>
            <div style={{ display: "none " }}>
              {(keyTypes = keyTypes.filter((key) => key !== apiKey.type))}
            </div>
          </Accordion>
        ))}
        {keyTypes.map((key) => (
          <Accordion key={key}>
            <AccordionSummary
              expandIcon={<ExpandMoreIcon />}
              aria-controls={`panel-${key}-content`}
              id={`panel-${key}-header`}
            >
              <Typography>{key}</Typography>
            </AccordionSummary>
            <AccordionDetails>
              <ApiKeysForm type={key} publicKey="" secretKey="" />
            </AccordionDetails>
          </Accordion>
        ))}
      </Box>
    );
  }

  return <span>Error: {error?.message}</span>;
}

export { ApiKeys };
