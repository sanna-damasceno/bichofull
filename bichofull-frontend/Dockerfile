# Estágio 1: Build (Alterado para Node 22)
FROM node:22 AS build
WORKDIR /app

# Copia os arquivos de dependências
COPY package*.json ./
RUN npm install

# Copia o restante do código e gera o build
COPY . .
RUN npm run build --configuration=production

# Estágio 2: Server
FROM nginx:alpine
# Lembre-se de verificar se o nome da pasta em /dist é exatamente 'bichofull-frontend'
COPY --from=build /app/dist/bichofull-frontend/browser /usr/share/nginx/html

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]