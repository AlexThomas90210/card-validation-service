#!/usr/bin/env bash

# Package the application
aws cloudformation package \
   --template-file ./master-template.yaml \
   --output-template-file ./master-output.yaml \
   --s3-bucket {{BUCKET_NAME}}

# Deploy the application
aws cloudformation deploy \
   --template-file ./master-output.yaml \
   --stack-name {{STACK_NAME}} \
   --capabilities CAPABILITY_IAM \
   --profile {{CLI_PROFILE_NAME}} \
   --parameter-overrides Stage=production \
   --region {{AWS_REGION}}

# Remove the generated output yaml file
rm master-output.yaml