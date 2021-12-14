import { ReactElement, useContext, useState } from "react";
import { UserAxiosContext } from "../../contexts/UserAxiosContext";
import { useFormik } from "formik";
import { ApiKey, ApiKeyValidationSchema } from "../../schemas/ApiKey";
import { axiosInstance } from "../../axios/axios";
import { apiUrl } from "../../constants/urls";
import { AxiosError, AxiosResponse } from "axios";
import { ApiKeysResponse } from "../../types/axios-responses/ApiKeysResponse";
import { Response } from "../../types/axios-responses/Response";
import { Box, Button, Typography } from "@mui/material";
import CircularProgress from "@mui/material/CircularProgress";
import { FormikFormControl } from "../FormirkFormControl/formik-form-control";

function ApiKeysForm({
  type,
  publicKey,
  secretKey,
}: {
  type: string;
  publicKey: string;
  secretKey: string;
}): ReactElement {
  const [formSubmitState, setFormSubmitState] = useState({
    formIsSubmitting: false,
    submitMessage: "",
  });

  const { userAxiosConfig } = useContext(UserAxiosContext);

  const { axiosConfig } = userAxiosConfig;

  const formik = useFormik({
    initialValues: {
      type,
      publicKey,
      secretKey,
    },
    validationSchema: ApiKeyValidationSchema,
    onSubmit: (values: ApiKey) => {
      setFormSubmitState({
        formIsSubmitting: true,
        submitMessage: "Submitting, please do NOT refresh.",
      });

      axiosInstance(axiosConfig)
        .post(apiUrl(), values)
        .then((successResponse: AxiosResponse<ApiKeysResponse>) => {
          const data: ApiKeysResponse = successResponse.data;
          setFormSubmitState({
            formIsSubmitting: false,
            submitMessage: data.message,
          });
        })
        .catch((errorResponse: AxiosError<Response>) => {
          const data: Response | undefined = errorResponse?.response?.data;

          setFormSubmitState({
            formIsSubmitting: false,
            submitMessage:
              data?.message !== undefined
                ? data.message
                : "Something went wrong, please let us know your issue by contacting us. So sorry!",
          });
        });
    },
  });

  return (
    <Box width="100%" maxWidth="800px" margin="auto">
      {formSubmitState.formIsSubmitting ? (
        <CircularProgress />
      ) : (
        <form action="POST" onSubmit={formik.handleSubmit}>
          <p>{formSubmitState.submitMessage}</p>
          <Typography variant="h4" gutterBottom={true}>
            {type} Key
          </Typography>

          <FormikFormControl
            formik={formik}
            fieldLabel={"PUBLIC Key"}
            fieldName={"publicKey"}
            fieldType={"string"}
            helperText={""}
          />

          <FormikFormControl
            fieldLabel={"PRIVATE key"}
            formik={formik}
            fieldName={"secretKey"}
            fieldType="password"
            helperText={"Make sure you don't share this with anyone else"}
          />

          <Button
            sx={{ height: 50, marginY: 5 }}
            fullWidth
            size="large"
            variant="contained"
            type="submit"
          >
            Submit
          </Button>
        </form>
      )}
    </Box>
  );
}

export { ApiKeysForm };
