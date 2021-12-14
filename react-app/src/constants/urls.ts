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

export { urlPrefix, userRegisterUrl, userLoginUrl, apiUrl };
