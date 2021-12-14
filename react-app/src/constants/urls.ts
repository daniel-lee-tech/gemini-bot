const urlPrefix = (): string => {
  if (!process.env.NODE_ENV || process.env.NODE_ENV === "development") {
    return "http://localhost:8080";
  }

  return "https://www.gemini-bot.com";
};

const userRegisterUrl = (): string => {
  return "/user/register";
};

const userLoginUrl = (): string => {
  return "/user/login";
};

const apiUrl = (): string => {
  return "/apikeys";
};

const transfersUrl = (): string => {
  return "/transfers";
};

const transfersImportUrl = (): string => {
  return "/transfers/import";
};

export {
  urlPrefix,
  userRegisterUrl,
  userLoginUrl,
  apiUrl,
  transfersUrl,
  transfersImportUrl,
};
