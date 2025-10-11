# syntax=docker/dockerfile:1

ARG NODE_VERSION=22.15.0

FROM node:${NODE_VERSION}-alpine as base

# Set working directory for all build stages.
WORKDIR /usr/src/app

# Create a stage for installing production dependecies.
FROM base as deps

RUN --mount=type=bind,source=package.json,target=package.json \
    --mount=type=bind,source=package-lock.json,target=package-lock.json \
    --mount=type=cache,target=/root/.npm \
    npm ci --omit=dev

FROM deps as build

RUN --mount=type=bind,source=package.json,target=package.json \
    --mount=type=bind,source=package-lock.json,target=package-lock.json \
    --mount=type=cache,target=/root/.npm \
    npm ci

COPY . .
# Run the build script. Creates vavascript files that form the logic of the website
RUN npm run build

FROM base as final

# Use production node environment by default.
ENV NODE_ENV production

# Run the application as a non-root user.
USER node

# Copy package.json so that package manager commands can be used.
COPY package*.json .

# Copy the production dependencies from the deps stage and also
# the built application from the build stage into the image.
COPY --from=deps /usr/src/app/node_modules ./node_modules
COPY --from=build /usr/src/app/dist/ ./dist

################################################################################
# Stage 4: Production Server with Nginx
# This stage uses a minimal Nginx image to serve the built application.
# It is the final image that will be run.
################################################################################
FROM nginx:alpine as serve-final

# Copy the static web files from the 'build' stage to the Nginx public directory.
# The path in the 'dist' folder may need to be adjusted based on your Angular project's output.
RUN rm /usr/share/nginx/html/index.html
COPY --from=build /usr/src/app/dist/RememberThis/browser /usr/share/nginx/html


# Expose port 80 to the host, as Nginx runs on this port by default.
EXPOSE 80