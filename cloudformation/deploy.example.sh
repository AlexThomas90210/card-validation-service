#!/usr/bin/env bash
# Note you will need to create a keypair in the region you run this template in and also generate a GitHub
# OAuth Token for CodePipeline to access your repo.
#

# Package the application
aws cloudformation package \
   --template-file ./master-template.yaml \
   --output-template-file ./master-output.yaml \
   --s3-bucket {{BUCKET_NAME}} \
   --profile {{CLI_PROFILE_NAME}}


# Deploy the application
aws cloudformation deploy \
   --template-file ./master-output.yaml \
   --stack-name {{STACK_NAME}} \
   --capabilities CAPABILITY_NAMED_IAM \
   --profile {{CLI_PROFILE_NAME}} \
   --parameter-overrides GithubToken={{GitHub_OAuth_Token}} \
   --region {{AWS_REGION}}

# Remove the generated output yaml file
rm master-output.yaml