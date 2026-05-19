# Setting Up LLM Providers

This section explains how to set up local and cloud-based Large Language Models (LLMs) that will be used throughout the course.

## Ollama (Local LLM Runtime)

* Use the provided Docker Compose YAML file to start the Ollama container.

```bash
docker compose up
```
* Make sure the container is running before continuing.
* You can explore the available models here: [https://ollama.com/search](https://ollama.com/search)
* Popular models
  - [gemma3](https://ollama.com/library/gemma3)
  - [gemma4](https://ollama.com/library/gemma4)
  - [mistral](https://ollama.com/library/mistral)
  - [qwen](https://ollama.com/library/qwen3.5)
  - [llama3](https://ollama.com/library/llama3)
* To run a specific model, for example Gemma 3

```bash
docker exec -it ollama bash
ollama run gemma3:270m
```

* After the model starts, you can interact with it directly from the terminal:

```
hi
what is the date today?
what is the capital of the United States?
```

* To exit the interactive session:

```
/bye
```

## OpenAI

* Go to [https://platform.openai.com/](https://platform.openai.com/) and create an account. Your country must be listed in the supported regions: [https://platform.openai.com/docs/supported-countries](https://platform.openai.com/docs/supported-countries)

* After logging in, navigate to **Settings → API Keys** and create a new API key.

  * Store the API key securely.
  * If the key is compromised, delete it immediately and generate a new one.

* Navigate to **Settings → Limits** to review rate limits.

  * The free tier allows only a limited number of requests per minute, depending on the model.
  * To use the API beyond trial usage, add billing

* Navigate to **Settings → Billing** and add billing information (when you need higher quotas / rate limits).

  * Minimum required balance is USD 5.
  * Disable automatic recharge.

* [Check model costs here](https://openai.com/api/pricing/)

## Google AI Studio (Gemini)

* Go to [Google AI Studio](https://aistudio.google.com/)
* Navigate to **Get API Key → Create API Key**
  - This creates a key tied to a Google Cloud project.
* If you need higher quotas / rate limits, set up billing (this might take you Google Cloud page).
* [Check model costs here](https://ai.google.dev/gemini-api/docs/pricing)

    
